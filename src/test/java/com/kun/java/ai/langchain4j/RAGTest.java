package com.kun.java.ai.langchain4j;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

@SpringBootTest
 public class RAGTest {

    /**
     * 读取文档
      */
 @Test
 public void testReadDocument() {
     //使用FileSystemDocumentLoader读取指定目录下的知识库文档
    //并使用默认的文档解析器TextDocumentParser对文档进行解析
    /*Document document = FileSystemDocumentLoader.loadDocument("D:/BaiduNetdiskDownload/资料/knowledge/测试.txt");
     System.out.println(document.text());*/

  /*// 加载单个文档
  Document document = FileSystemDocumentLoader.loadDocument("D:/BaiduNetdiskDownload/资料/knowledge/file.txt", new
          TextDocumentParser());
  // 从一个目录中加载所有文档
  List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:/BaiduNetdiskDownload/资料/knowledge", new
          TextDocumentParser());*/

  // 从一个目录中加载所有的.txt文档
  PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
  List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:/BaiduNetdiskDownload/资料/knowledge",
          pathMatcher, new TextDocumentParser());


  /*// 从一个目录及其子目录中加载所有文档
  List<Document> documents =
          FileSystemDocumentLoader.loadDocumentsRecursively("D:/BaiduNetdiskDownload/资料/knowledge", new
                  TextDocumentParser());*/
   }

 /**
  * 解析PDF
  */
 @Test
 public void testParsePDF() {
  Document document = FileSystemDocumentLoader.loadDocument(
          "E:/knowledge/医院信息.pdf",
          new ApachePdfBoxDocumentParser()
  );
  System.out.println(document);
 }


 /**
  * 加载文档并存入向量数据库
  */
 @Test
 public void testReadDocumentAndStore() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        Document document = FileSystemDocumentLoader.loadDocument("D:/BaiduNetdiskDownload/资料/knowledge/人工智能.md");
        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

      //自定义文档分割器
      //按段落分割文档：每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
      //注意：当段落长度总和小于设定的最大长度时，就不会有重叠的必要。
      DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(
              300,
              30,
              //token分词器：按token计算
              new HuggingFaceTokenizer());

      //按字符计算:向量转换以及向量存储
      //DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30);
      EmbeddingStoreIngestor
              .builder()
              .embeddingStore(embeddingStore)
              .documentSplitter(documentSplitter)
              .build()
              .ingest(document);
 }

     @Test
     public void testTokenCount() {
      String text = "这是一个示例文本，用于测试 token 长度的计算。";
      UserMessage userMessage = UserMessage.userMessage(text);
      //计算 token 长度
      //OpenAiTokenCountEstimator tokenizer = new OpenAiTokenCountEstimator("openAiChatModel");
      HuggingFaceTokenizer tokenizer = new HuggingFaceTokenizer();

      int count = tokenizer.estimateTokenCountInMessage(userMessage);
      System.out.println("token长度：" + count);
     }

 }