package com.yihe.common.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 导出pdf 通用方法工具类
 *
 * @author 丫丫
 * @date 2021-12-30
 */
public class DownloadPDFUtils {
    /**
     * 创建文本对象
     * @param response
     * @param file 文件名
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Document getDocument(HttpServletResponse response,String file) throws IOException, DocumentException {
        // 1-创建文本对象 Document
        Document document = new Document(PageSize.A4, 50, 50, 75, 50);
        response.setHeader("content-Type", "application/pdf");
        // 下载文件的默认名称
        String fileName = new String(file.getBytes("GBK"), "ISO8859-1").toString();
        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
        return document;
    }

    /**
     * 支持中文
     *
     * @return 返回默认的字体样式
     */
    public static Font getChineseFont() {
        BaseFont bfChinese;
        Font fontChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fontChinese = new Font(bfChinese, 12,Font.NORMAL,BaseColor.BLACK);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }

    /**
     * 支持中文
     * @param fontSize 可改变字体大小
     * @return
     */
    public static Font getChineseFont(int fontSize) {
        BaseFont bfChinese;
        Font fontChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fontChinese = new Font(bfChinese, fontSize,Font.NORMAL,BaseColor.BLACK);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }

    /**
     * 支持中文
     * @param fontSize 可改变字体大小
     * @param font 可改变字体粗细
     * @return
     */
    public static Font getChineseFont(int fontSize, int font) {
        BaseFont bfChinese;
        Font fontChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fontChinese = new Font(bfChinese, fontSize,font,BaseColor.BLACK);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }

    /**
     * 支持中文
     * @param fontSize 可改变字体大小
     * @param font 可改变字体粗细
     * @param font 可改变字体颜色
     * @return
     */
    public static Font getChineseFont(int fontSize, int font, BaseColor color) {
        BaseFont bfChinese;
        Font fontChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fontChinese = new Font(bfChinese, fontSize,font,color);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }

    /**
     * 设置用户信息单元格
     * @param content 内容
     * @param cellcolor 背景颜色
     * @return
     */
    public static PdfPCell getcell(String content, BaseColor cellcolor){
        PdfPCell cell=new PdfPCell(new Paragraph(content, getChineseFont()));
        if(cellcolor!=null){
            cell.setBackgroundColor(cellcolor);//设置背景色
        }
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
        cell.setFixedHeight(25f);//固定高度
        return cell;
    }

    /**
     * 设置单元格
     * @param content 内容
     * @return
     */
    public static PdfPCell getcell1(String content, Boolean falg){
        PdfPCell cell=new PdfPCell(new Paragraph(content, getChineseFont()));
        cell.setHorizontalAlignment(falg ? Element.ALIGN_CENTER : Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
        cell.setMinimumHeight(25f);//固定高度
        cell.setPadding(5f);
        cell.setLeading(2,1);
        return cell;
    }

    /**
     * 设置用户评估结果单元格
     * @param content 内容
     * @param fontcolor 字体颜色
     * @param cellcolor 背景颜色
     * @return
     */
    public static PdfPCell getcell(String content,BaseColor fontcolor,BaseColor cellcolor){
        PdfPCell cell=new PdfPCell(new Paragraph(content, getChineseFont(10,Font.NORMAL,fontcolor)));
        if(cellcolor!=null){
            cell.setBackgroundColor(cellcolor);//设置背景色
        }
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
        cell.setFixedHeight(20f);//固定高度
        cell.setBorder(Rectangle.NO_BORDER);//无边框
        return cell;
    }

    /**
     * 设置pdf报告的标题以及logo
     * @param document
     * @param content 标题内容
     * @param logo logo网络路径
     */
    public static void setFileTitle(Document document, String content, String logo) throws IOException, DocumentException {
        Paragraph info = new Paragraph();
        Chunk tChunk2 = null;
        if(content.length() <= 9){
            tChunk2 = new Chunk(content, getChineseFont(30,Font.BOLD,BaseColor.BLACK));
        }else{
            tChunk2 = new Chunk(content, getChineseFont(28,Font.BOLD,BaseColor.BLACK));
        }
        info.add(tChunk2);
        Image img = Image.getInstance(logo);
        img.setAlignment(Image.RIGHT);
        img.setBorder(Image.BOX);
        img.setBorderWidth(1);
        img.setBorderColor(BaseColor.WHITE);
        img.scaleToFit(1000, 72);// 大小
        img.setAbsolutePosition(350,730);
        info.add(img);
        document.add(info);
        document.add(new Paragraph("\n\n"));
    }

    /**
     * 添加pdf报告的用户信息
     * @param document
     * @param tableName 表头
     * @param userInfo 内容
     */
    public static void setUserInfo(Document document, String[] tableName, String[] userInfo) throws DocumentException {
        //创建表格
        PdfPTable table = new PdfPTable(tableName.length);
        //定义表格宽度
        int[] cellsWidth = new int[tableName.length];
        for (int i =0; i < tableName.length; i++) cellsWidth[i] = 1;
        table.setWidths(cellsWidth);// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        // 添加表头元素
        for (int i = 0; i < tableName.length; i++) {
            table.addCell(getcell(tableName[i],new BaseColor(237,244,255)));
        }
        // 添加用户信息
        for (int i = 0; i < userInfo.length; i++) {
            table.addCell(getcell(userInfo[i],null));
        }
        table.setSpacingAfter(10f);// 设置表格下面空白行
        document.add(table);// 把表格加入文档
    }

    /**
     * 添加pdf报告成绩的第一个表格
     * @param document
     * @param score 分数
     * @param cellimg 图片
     */
    public static void setGradeIcon(Document document, int score, Image cellimg) throws DocumentException {
        PdfPTable table  = new PdfPTable(3);
        table.setSpacingBefore(10f);// 设置表格上面空白行
        int [] cellsWidth = new int[]{score-2,3,99-score};
        table.setWidths(cellsWidth);// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        for (int i=0; i<3; i++){
            if(i==1){
                PdfPCell cell=new PdfPCell(cellimg);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell.setFixedHeight(20f);//固定高度
                cell.setBorder(Rectangle.NO_BORDER);//无边框
                table.addCell(cell);
            }else{
                table.addCell(getcell("",BaseColor.BLACK, null));
            }
        }
        document.add(table);
    }

    /**
     * 添加pdf报告成绩的第二个表格
     * @param document
     * @param cellsWidth 单元格宽度
     * @param content 内容数组
     * @param colors 背景色数组
     */
    public static void setGradeIcon(Document document, int[] cellsWidth, String[] content, BaseColor[] colors) throws DocumentException {
        PdfPTable table  = new PdfPTable(cellsWidth.length);
        table.setWidths(cellsWidth);// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        for (int i = 0; i < content.length; i++){
            table.addCell(getcell(content[i],BaseColor.WHITE,colors[i]));
        }
        document.add(table);
    }

    /**
     * 添加pdf报告成绩的第三个表格（仅适用三个分值段的表格）
     * @param document
     * @param cellsWidth 单元格宽度（约定0,3,6,9长度为10）
     * @param content 分值
     * @throws DocumentException
     */
    public static void setGradeIcon(Document document, int[] cellsWidth, String[] content) throws DocumentException {
        PdfPTable table  = new PdfPTable(cellsWidth.length);
        table.setWidths(cellsWidth);// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        for (int i = 0; i < cellsWidth.length; i++) {
            if (i == 0){
                PdfPCell cell= DownloadPDFUtils.getcell(content[0],BaseColor.BLACK,null);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }else if (i == cellsWidth.length-1){
                PdfPCell cell= DownloadPDFUtils.getcell(content[i/2],BaseColor.BLACK,null);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }else if (i%2 == 0){
                table.addCell(DownloadPDFUtils.getcell(content[i/2],BaseColor.BLACK,null));
            }else{
                table.addCell(DownloadPDFUtils.getcell("",BaseColor.BLACK, null));
            }
        }
        document.add(table);
    }

    /**
     * 设置二级标题
     * @param document
     * @param content 内容
     */
    public static void setSecondaryTitel(Document document,String content) throws DocumentException {
        PdfPTable table  = new PdfPTable(1);
        table.setWidths(new int[]{1});// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        PdfPCell cell = new PdfPCell(new Paragraph(content, getChineseFont(16,Font.BOLD,BaseColor.BLACK)));
        cell.setBackgroundColor(new BaseColor(230,247,255));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorderColor(new BaseColor(200,234,255));
        cell.setFixedHeight(25f);//设置单元格高度
        cell.setPaddingLeft(10f);//设置单元格左边距
        table.addCell(cell);
        table.setSpacingAfter(5f);//下方留白
        table.setSpacingBefore(10f);//上方留白
        document.add(table);
    }

    /**
     * 设置三级标题
     * @param document
     * @param content 内容
     */
    public static void setThreeTitel(Document document, String content) throws DocumentException {
        document.add(new Paragraph(content, getChineseFont(12,Font.NORMAL,new BaseColor(56,164,255))));
    }

    /**
     * 设置三级标题
     * @param document
     * @param content 内容
     * @param color 标题头的颜色
     */
    public static void setThreeTitel(Document document, String content, BaseColor color) throws DocumentException {
        Paragraph paragraph= new Paragraph();
        Chunk chunk=new Chunk("·",getChineseFont(24,Font.BOLD,color));
        chunk.setTextRise(-4);
        paragraph.add(chunk);
        chunk=new Chunk(content,getChineseFont(14,Font.BOLD));
        paragraph.add(chunk);
        paragraph.setSpacingBefore(10f);
        paragraph.setSpacingAfter(5f);
        document.add(paragraph);
    }

    /**
     * 设置列表
     * @param document
     * @param content 内容
     * @param image 列头图片
     */
    public static void setListContent(Document document, String[] content, Image image) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{1, 1});// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        int length=content.length%2 == 1 ? content.length+1 : content.length;
        for (int i=0; i < length ; i++){
            PdfPCell oneContent = null;
            if (i<content.length){
                PdfPTable datatable1 = new PdfPTable(2);
                datatable1.setWidths(new int[]{1, 9});// 单元格宽度
                image.scaleToFit(10, 10);// 大小
                PdfPCell cell1 = new PdfPCell(image);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell1.setBorder(Rectangle.NO_BORDER);//无边框
                datatable1.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Paragraph(content[i], getChineseFont()));
                cell2.setBorder(Rectangle.NO_BORDER);//无边框
                cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                datatable1.addCell(cell2);
                oneContent = new PdfPCell(datatable1);
            }else{
                oneContent = new PdfPCell(new Paragraph("", getChineseFont()));
            }
            oneContent.setHorizontalAlignment(Element.ALIGN_LEFT);
            oneContent.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            oneContent.setBorder(Rectangle.NO_BORDER);//无边框
            oneContent.setFixedHeight(25f);//设置单元格高度
            oneContent.setPaddingLeft(4f);//设置单元格左边距
            table.addCell(oneContent);
        }
        document.add(table);
    }

    /**
     * 设置段落
     * @param document
     * @param content 内容
     */
    public static void setParagraph(Document document, String content) throws DocumentException {
        Paragraph paragraph=new Paragraph(content,getChineseFont());
        paragraph.setFirstLineIndent(24f);
        paragraph.setSpacingAfter(5f);
        document.add(paragraph);
    }

    /**
     * 设置结尾处的灰色线条
     * @param document
     */
    public static void setEndline(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidths(new int[]{1});// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        PdfPCell listcell=new PdfPCell(new Paragraph("", getChineseFont()));
        listcell.setBackgroundColor(new BaseColor(234,234,234));
        listcell.setBorder(Rectangle.NO_BORDER);
        listcell.setFixedHeight(5f);
        table.addCell(listcell);
        table.setSpacingAfter(5f);
        table.setSpacingBefore(10f);
        document.add(table);
    }

    /**
     * 设置水印，声明
     * @param pdfWriter
     * @throws IOException
     * @throws DocumentException
     */
    public static void setStatement(PdfWriter pdfWriter) throws IOException, DocumentException {
        PdfContentByte under = pdfWriter.getDirectContentUnder();
        under.beginText();
        BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        under.setFontAndSize(bf, 12);
        under.setColorFill(BaseColor.BLACK);
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.6f);// 设置透明度为0.8
        under.setGState(gs);
        under.showTextAligned(Element.ALIGN_LEFT, "声明：本报告结论和建议不作为诊断或治疗依据，任何医疗行为请遵从医嘱！", 92, 45, 0);
        under.endText();
    }

    /**
     * 中医体质辨识结果导出专属方法
     * @param document
     * @param result
     * @throws DocumentException
     */
    public static void setChinese(Document document, String[] result) throws DocumentException {
        //创建表格
        PdfPTable table = new PdfPTable(10);
        //定义表格宽度
        int[] cellsWidth = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        table.setWidths(cellsWidth);// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        String[] syndrome = {"体质辨识", "平和质（A型）", "气虚质（B型）", "阳虚质（C型）", "阴虚质（D型）",
            "痰湿质（E型）", "湿热质（F型）", "血瘀质（G型）", "气郁质（H型）", "特禀质（I型）"};
        // 添加第一行元素
        for (int i = 0; i < syndrome.length; i++) {
            if (i == 0){
                table.addCell(getcell(syndrome[i],new BaseColor(237,244,255)));
            }else{
                table.addCell(getcell(syndrome[i],null));
            }
        }
        // 添加第二行元素
        for (int i = 0; i < result.length; i++) {
            if (i == 0){
                table.addCell(getcell(result[i],new BaseColor(237,244,255)));
            }else{
                table.addCell(getcell(result[i],null));
            }
        }
        table.setSpacingAfter(10f);// 设置表格下面空白行
        document.add(table);// 把表格加入文档
    }

    /**
     * 设置中医体质辨识结果表格
     * @param document
     * @param content
     * @throws DocumentException
     */
    public static void setChineseTable(Document document, List<String> content) throws DocumentException {
        //创建表格
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{3, 7});// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        for (int i = 0; i < content.size(); i++) {
            if (i < 2){
                table.addCell(getcell(content.get(i),new BaseColor(237,244,255)));
            }else{
                table.addCell(getcell1(content.get(i), i%2 ==1? false : true));
            }
        }
        table.setSpacingBefore(10f);
        table.setSpacingAfter(5f);// 设置表格下面空白行
        document.add(table);
    }

    public static void setPsychologyTable(Document document,List<String> content) throws DocumentException {
        //创建表格
        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[]{2, 1, 7});// 单元格宽度
        table.setWidthPercentage(100);// 表格的宽度百分比
        for (int i = 0; i < content.size(); i++) {
            if (i < 3){
                table.addCell(getcell(content.get(i),new BaseColor(237,244,255)));
            }else{
                table.addCell(getcell1(content.get(i), i%3 ==2? false : true));
            }
        }
        table.setSpacingBefore(10f);
        table.setSpacingAfter(5f);// 设置表格下面空白行
        document.add(table);
    }
}
