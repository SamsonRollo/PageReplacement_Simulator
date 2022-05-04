package model;

import javax.swing.JPanel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

public class ImageSaver{
	private JPanel panel;
	public boolean hasError = false;

	public ImageSaver(){}

	public ImageSaver(JPanel panel){
		this.panel = panel;
	}	

	public void saveAsImage(){
		try{
			BufferedImage bi = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
			panel.paint(bi.getGraphics());
			ImageIO.write(bi, "PNG", new File(getFileName()+".png"));
		}catch(Exception e){
			hasError = true;
		}

	}

	public void saveAsPDF(){
		Document document = new Document();
		try {
			BufferedImage bi = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
			panel.paint(bi.getGraphics());

			int width = bi.getWidth();
			int height = bi.getHeight();

			// BufferedImage bi2 = new BufferedImage(width,height,bi.getType());
			// Graphics2D graphics2D = bi2.createGraphics();
   //  		graphics2D.translate((height - width) / 2, (height - width) / 2);
 		//     graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
   //  		graphics2D.drawRenderedImage(bi, null);

		    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(getFileName()+".pdf")));
		    document.open();
		    
		    Image itImage = Image.getInstance(writer, bi, 1);
		    itImage.setAbsolutePosition(50, 50);
            itImage.scalePercent(25);
            document.add(itImage);

		} catch (Exception e) {
		    hasError = true;
		}
		finally{
		    if(document.isOpen()){
		        document.close();
		    }
		}
	}

	public String getFileName(){
		LocalDateTime now = LocalDateTime.now();
		String format = now.format(DateTimeFormatter.ofPattern("MMddyy_HHmmss", Locale.ENGLISH));
		return format+"_PG";
	}

	public boolean getHasError(){
		return this.hasError;
	}

	// PdfContentByte contentByte = writer.getDirectContent();
	// 	    PdfTemplate template = contentByte.createTemplate(500, 500);
	// 	    Graphics2D g2 = template.createGraphics(500, 500);
	// 	    panel.print(g2);
	// 	    g2.dispose();
	// 	    contentByte.addTemplate(template, 30, 300);
}