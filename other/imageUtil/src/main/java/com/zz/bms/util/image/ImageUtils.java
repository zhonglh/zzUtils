package com.zz.bms.util.image;

/**
 * Created by Work on 2017/5/19.
 * 图片工具类
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageUtils {

    // 水印透明度
    private static float alpha = 0.2f;
    // 水印横向位置
    private static int positionWidth = 150;
    // 水印纵向位置
    private static int positionHeight = 300;
    // 水印文字字体
    private static Font font = new Font("宋体", Font.BOLD, 72);
    // 水印文字颜色
    private static Color color = Color.red;
    /**
     * 设置添加水印的属性
     *
     * @param alpha
     *            水印透明度
     * @param positionWidth
     *            水印横向位置
     * @param positionHeight
     *            水印纵向位置
     * @param font
     *            水印文字字体
     * @param color
     *            水印文字颜色
     */
    public static void setImageMarkOptions(float alpha, int positionWidth,
                                           int positionHeight,Font font, Color color) {
        if (alpha != 0.0f)
            ImageUtils.alpha = alpha;
        if (positionWidth != 0)
            ImageUtils.positionWidth = positionWidth;
        if (positionHeight != 0)
            ImageUtils.positionHeight = positionHeight;
        if (font != null)
            ImageUtils.font = font;
        if (color != null)
            ImageUtils.color = color;
    }
    /**
     * 给图片添加水印图片
     *
     * @param iconPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath) {
        markImageByIcon(iconPath, srcImgPath, targerPath, null);
    }
    public static byte[] markImageByIcon(String iconPath, String srcImgPath) throws IOException {
        BufferedImage bufferedImage = markImageByIcon(iconPath, srcImgPath, null, null);
        byte[] bytes = getbyte(bufferedImage, new File(srcImgPath));
        return bytes;
    }
    public static byte[] markImageByIcon(File iconfile,File secfile) throws IOException {
        BufferedImage bufferedImage = markImageByIcon(iconfile.getPath(), secfile.getPath(), null, null);
        byte[] bytes = getbyte(bufferedImage, secfile);
        return bytes;
    }
    public static void markImageByIcon(File iconfile,File secfile,File outfile) throws IOException {
        BufferedImage bufferedImage = markImageByIcon(iconfile.getPath(), secfile.getPath(), outfile.getPath(), null);
    }
    private static byte[] getbyte(BufferedImage bufferedImage ,File file) throws IOException {
        String fileName=file.getName();
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, prefix, os);
        byte[] bytes = os.toByteArray();
        return bytes;
    }
    /**
     * 给图片添加图片水印、可设置水印图片旋转角度
     * @param iconPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree 水印图片旋转角度
     */
    public static BufferedImage markImageByIcon(String iconPath, String srcImgPath,
                                                String targerPath, Integer degree) {
        OutputStream os = null;
        BufferedImage buffImg=null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));

            buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D g = buffImg.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg
                                .getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            Image img = imgIcon.getImage();

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            g.drawImage(img, positionWidth, positionHeight, null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();
            if (targerPath != null) {
                os = new FileOutputStream(targerPath);
                ImageIO.write(buffImg, "JPG", os);
                System.out.println("图片完成添加水印图片。。。。。。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buffImg;
    }

    /**
     * 给图片添加水印文字
     *
     * @param logoText
     *          水印文字
     * @param srcImgPath
     *          源图片路径
     * @param targerPath
     *          目标图片路径
     */
    public static void markImageByText(String logoText, String srcImgPath,
                                       String targerPath) {
        markImageByText(logoText, srcImgPath, targerPath, null);
    }
    public static byte[] markImageByText(String logoText, String srcImgPath) throws IOException {
        BufferedImage bufferedImage = markImageByText(logoText, srcImgPath, null, null);
        byte[] bytes = getbyte(bufferedImage, new File(srcImgPath));
        return bytes;
    }
    public static byte[] markImageByText(String logoTest,File srcImgPath) throws IOException {
        BufferedImage bufferedImage = markImageByText(logoTest, srcImgPath.getPath(), null, null);
        byte[] bytes = getbyte(bufferedImage, srcImgPath);
        return bytes;
    }
    public static void markImageByText(String logoTest,File srcImgPath,String targerPath) throws IOException {
        markImageByText(logoTest, srcImgPath.getPath(), targerPath, null);
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText 水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree 旋转角度
     */
    public static BufferedImage markImageByText(String logoText, String srcImgPath,
                                                String targerPath, Integer degree) {

        InputStream is = null;
        OutputStream os = null;
        BufferedImage buffImg = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D g = buffImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            if (null != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            g.setColor(color);
            g.setFont(font);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawString(logoText, positionWidth, positionHeight);

            g.dispose();
            if (targerPath!=null){
                os = new FileOutputStream(targerPath);
                ImageIO.write(buffImg, "JPG", os);

                System.out.println("图片完成添加水印文字。。。。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buffImg;
    }
    /**
     * 旋转图片为指定角度
     *
     * @param srcPath  doem:
     *              BufferedImage src = ImageIO.read(new File("E:/wate/0002.png"));
     *            目标图像
     * @param angel
     *            旋转角度
     * @return
     * @throws IOException
     */
    public static byte[] Rotate(String srcPath,int angel) throws IOException {
        BufferedImage bufferedImage = Rotate(new File(srcPath), null,angel);
        byte[] bytes = getbyte(bufferedImage, new File(srcPath));
        return bytes;
    }
    public static void Rotate(String srcPath,String outPath,int angel) throws IOException {
        Rotate(new File(srcPath), outPath,angel);
    }
    public static byte[] Rotate(File srcPath,int angel) throws IOException {
        BufferedImage rotate = Rotate(srcPath, null, angel);
        byte[] bytes = getbyte(rotate, srcPath);
        return bytes;
    }
    public static BufferedImage Rotate(File src, String outPath,int angel) throws IOException {
        int src_width = ImageIO.read(src).getWidth(null);
        int src_height = ImageIO.read(src).getHeight(null);
        // calculate the new image size
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);

        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // transform
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

        g2.drawImage(ImageIO.read(src), null, null);
        if (outPath!=null){
            String fileName=src.getName();
            String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
            ImageIO.write(res,prefix, new FileOutputStream(outPath));
        }
        return res;
    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree, we need to do some conversion
        if (angel >= 90) {
            if(angel / 90 % 2 == 1){
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }
    /**
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
     * @param widthdist 压缩后图片宽度（当rate==null时，必传）
     * @param heightdist 压缩后图片高度（当rate==null时，必传）
     * @param rate 压缩比例
     */
    public static byte[] reduceImg(File file, int widthdist, int heightdist, Float rate) throws IOException {
        BufferedImage bufferedImage=null;
        if (widthdist==0 & heightdist ==0){
            bufferedImage = Thumbnails.of(file.getPath()).scale(rate).asBufferedImage();
        }
        if (rate ==null){
            bufferedImage = Thumbnails.of(file.getPath()).size(widthdist, heightdist).asBufferedImage();
        }
        String fileName=file.getName();
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, prefix, os);
        byte[] bytes = os.toByteArray();
        return bytes;
    }

    public static byte[] reduceImg(File file,  Float rate) throws IOException {
        return reduceImg(file,0,0,rate);
    }
    public static byte[] reduceImg(File file ,  int widthdist ,int heighdist) throws IOException {
        return  reduceImg(file,widthdist,heighdist,null);
    }
    public static byte[] reduceImg(File file) throws IOException {
        return   reduceImg(file,0,0,0.6f);
    }
    public static byte[] reduceImg(String srcPath) throws IOException {
        return   reduceImg(new File(srcPath),0,0,0.6f);
    }
    public static byte[] reduceImg(String file ,  int widthdist ,int heighdist) throws IOException {
        return  reduceImg(new File(file),widthdist,heighdist,null);
    }
    public static byte[] reduceImg(String file,  Float rate) throws IOException {
        return reduceImg(new File(file),0,0,rate);
    }
    /**
     * 获取图片宽高
     *
     * @param file
     *            图片文件
     * @return 宽度 高度
     */
    public static int[] getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图宽
            result[1] = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @param srcFile 源文件
     * @param outFile 输出文件
     * @param x 坐标
     * @param y 坐标
     * @param width 宽度
     * @param height 高度
     * @描述 —— 裁剪图片
     */
    public static   byte[] cutPic(String srcFile, String outFile, int x, int y,
                                  int width, int height) {
        FileInputStream is = null;
        ImageInputStream iis = null;
        byte [] bytes =null;
        try {
            // 如果源图片不存在
            if (!new File(srcFile).exists()) {
                return bytes;
            }

            // 读取图片文件
            is = new FileInputStream(srcFile);

            // 获取文件格式
            String ext = srcFile.substring(srcFile.lastIndexOf(".") + 1);

            // ImageReader声称能够解码指定格式
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
            ImageReader reader = it.next();

            // 获取图片流
            iis = ImageIO.createImageInputStream(is);

            // 输入源中的图像将只按顺序读取
            reader.setInput(iis, true);

            // 描述如何对流进行解码
            ImageReadParam param = reader.getDefaultReadParam();

            // 图片裁剪区域
            Rectangle rect = new Rectangle(x, y, width, height);

            // 提供一个 BufferedImage，将其用作解码像素数据的目标
            param.setSourceRegion(rect);

            // 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象
            BufferedImage bi = reader.read(0, param);

            // 保存新图片
            File tempOutFile = new File(outFile);
            if (!tempOutFile.exists()) {
                tempOutFile.mkdirs();
            }
            if (outFile!=null){
                ImageIO.write(bi, ext, new File(outFile));
            }
            bytes = getbyte(bi, new File(srcFile));
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (iis != null) {
                    iis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    /**
     * <p>Discription:[convert GIF->JPG GIF->PNG PNG->GIF(X) PNG->JPG ]</p>
     * @param source 源文件路径
     * @param formatName 要转换的格式
     * @param result 新文件的路径
     */
    public static void convert(File source, String formatName, String result)
    {
        try
        {
            source.canRead();
            BufferedImage src = ImageIO.read(source);
            ImageIO.write(src, formatName, new File(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 将图片转换成pdf文件
     *imgFilePath 需要被转换的img所存放的位置。 例如imgFilePath="D:\\projectPath\\55555.jpg";
     *pdfFilePath 转换后的pdf所存放的位置 例如pdfFilePath="D:\\projectPath\\test.pdf";
     * @throws IOException
     */


    public static boolean img2Pdf(String imgFilePath, String pdfFilePath) throws IOException, DocumentException {
        File file=new File(imgFilePath);
        if(file.exists()){
            Document document = new Document();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pdfFilePath);
                PdfWriter.getInstance(document, fos);

                // 添加PDF文档的某些信息，比如作者，主题等等
                // document.addAuthor("arui");
                // document.addSubject("test pdf.");
                // 设置文档的大小
                document.setPageSize(PageSize.A4);
                // 打开文档
                document.open();
                // 读取一个图片
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imgFilePath);
                float imageHeight=image.getScaledHeight();
                float imageWidth=image.getScaledWidth();
                int i=0;
                while(imageHeight>500||imageWidth>500){
                    image.scalePercent(100-i);
                    i++;
                    imageHeight=image.getScaledHeight();
                    imageWidth=image.getScaledWidth();
                  //  System.out.println("imageHeight->"+imageHeight);
                    //System.out.println("imageWidth->"+imageWidth);
                }

                image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
                // 插入一个图片
                document.add(image);
            } catch (DocumentException de) {
                System.out.println(de.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            document.close();
            fos.flush();
            fos.close();
            return true;
        }else{
            return false;
        }
    }
}
