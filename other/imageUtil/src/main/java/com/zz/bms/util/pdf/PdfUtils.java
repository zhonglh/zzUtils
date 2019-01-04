package com.zz.bms.util.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;

/**
 * Created by Work on 2017/5/27.
 */
public class PdfUtils {
    /**
     * @param text  水印字体
     * @param inputPath  读pdf文件
     * @param fileName  生成的文件名
     * @param tempPath  生成临时文件
     * @param fontSize 字体大小
     * @param rotation  倾斜度
     * @return
     * @throws Exception
     */
    public static String insertTextToPdf(String text, String inputPath, String fileName, String tempPath, float fontSize, float rotation)throws Exception{
        //待加水印的文件
        PdfReader reader = new PdfReader(inputPath);

        String outputFile = tempPath + fileName;
        //加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

        int total = reader.getNumberOfPages()+1;

        PdfContentByte content;
        //设置字体
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                BaseFont.EMBEDDED);
        //水印文字
        String waterText=text;

        //循环对每页插入水印
        for (int i = 1; i < total; i++) {

            content = stamper.getUnderContent(i);

            float pageWidth = reader.getPageSize(i).getWidth();
            float pageHeigh = reader.getPageSize(i).getHeight();

            //开始
            content.beginText();
            //设置颜色
            content.setColorFill(BaseColor.GRAY);
            //设置字体及字号
            content.setFontAndSize(base, fontSize);
//			      content.setFontAndSize(base, 50);
            //设置起始位置
			      /*content.setTextMatrix(50, 50);*/

            content.showTextAligned(Element.ALIGN_CENTER, waterText, pageWidth/2, pageHeigh/2, rotation);//水印文字成35度角倾斜
//  	      content.showTextAligned(Element.ALIGN_CENTER, waterText, pageWidth/2, pageHeigh/2, -35);//水印文字成35度角倾斜

            content.endText();

        }
        stamper.close();
        return outputFile;
    }
    public static String insertTextToPdf(String text, String inputPath, String fileName, String tempPath) throws Exception {
        return insertTextToPdf(text,inputPath,  fileName,  tempPath,40,-45);
    }



    /**
     * PDF添加水印
     * @param watermarkPath 水印路径
     * @param inputPath pdf路径
     * @param fileName 生成的pdf名称
     * @param tempPath 生成pdf的路径（不待名称）
     * @param absoluteX 横坐标位置
     * @param absoluteY 纵坐标位置
     * @param deg  旋转度
     * @return
     */
    public static String insertWatermark(String watermarkPath,String inputPath,String fileName,String tempPath,float absoluteX,float absoluteY,float deg){
//			//图片水印
        PdfReader reader = null;
        PdfStamper stamp = null;
        try {

            reader = new PdfReader(inputPath);

            int pageSize = reader.getNumberOfPages();

            //将生成的有水印的文件先放到临时路径下
            String outputFile = tempPath + fileName;

            reader.unethicalreading = true;//针对一些PDP设置权限的问题，默认unethicalreading=false，代表没有权限，true 有权限

            stamp = new PdfStamper(reader, new FileOutputStream(outputFile));

            Image img = Image.getInstance(watermarkPath);// 插入水印

            // 设置水印旋转
            img.setAbsolutePosition(absoluteX, absoluteY);
            img.setRotationDegrees(deg);
            img.rotate();

            for(int i = 1; i <= pageSize; i++) {

                PdfContentByte under = stamp.getOverContent(i);

                under.addImage(img);

            }

            return  outputFile;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{

            try {
                stamp.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            } // 关闭
        }
    }
    public static String insertWatermark(String watermarkPath,String inputPath,String fileName,String tempPath){
        return  insertWatermark(watermarkPath,inputPath,fileName,tempPath,300,350,-45);
    }
}
