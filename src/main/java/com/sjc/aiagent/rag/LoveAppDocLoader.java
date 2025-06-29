package com.sjc.aiagent.rag;

import cn.hutool.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 恋爱大师应用文档读取加载器
 */
@Component
@Slf4j
public class LoveAppDocLoader {

    private final ResourcePatternResolver resolver;

    public LoveAppDocLoader(ResourcePatternResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * 加载多篇markdown文档
     */
    public List<Document> loadMD() {
        List<Document> allDocs = new ArrayList<>();
        try {
            Resource[] resources = resolver.getResources("classpath:document/*.md");
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                String status = filename.substring(filename.length() - 6, filename.length() - 4);

                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)
                        .withIncludeCodeBlock(false)
                        .withIncludeBlockquote(false)
                        .withAdditionalMetadata("filename", filename)
                        .withAdditionalMetadata("status", status)
                        .build();
                MarkdownDocumentReader markdownDocumentReader = new MarkdownDocumentReader(resource, config);
                allDocs.addAll(markdownDocumentReader.get());
            }
        } catch (Exception e) {
            log.error("Markdown 文档加载失败", e);
        }
        return allDocs;
    }
}
