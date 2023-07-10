/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
/**
 *
 * @author menna
 */
public class pdfsplitter {
    @SuppressWarnings("empty-statement")
    public Image render(File pdffile) throws IOException{
    PDDocument document = PDDocument.load(pdffile);
    PDFRenderer pdfRenderer = new PDFRenderer(document);
    BufferedImage img = pdfRenderer.renderImage(0);

//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    ImageIO.write(img, "jpg", new ByteArrayOutputStream());
//    byte[] data=baos.toByteArray();
//    ImageIcon imageIcon = new ImageIcon(data);
//    imageIcon.getImage();
    
    document.close();

    return SwingFXUtils.toFXImage(img, null);
    }
    public void split(File pdffile) throws IOException{
            PDDocument document = PDDocument.load(pdffile);

        // Splitter Class
        Splitter splitting = new Splitter();

        // Splitting the pages into multiple PDFs
        List<PDDocument> Page = splitting.split(document);

        // Using a iterator to Traverse all pages
        Iterator<PDDocument> iteration
                = Page.listIterator();
        
        // Saving each page as an individual document
        int j = 1;
        while (iteration.hasNext()) {
            PDDocument pd = iteration.next();
            pd.save(pdffile.getName()+"- "+ j++ + ".pdf");
        }
    }
    
    public void split(File pdffile,String range) throws IOException {
            PDDocument document = PDDocument.load(pdffile);
            Splitter splitting = new Splitter();
            List<PDDocument> Pages = splitting.split(document);
            int j = 1;
            range = range.replaceAll(" ","");
            String[] rangeArr = range.split("\\+");
            
            for(String str:rangeArr){   //1,3-7,8-10
                String [] pdfrange = str.split(",");         //[1],[3-7],[8-10]
                PDFMergerUtility PDFmerger = new PDFMergerUtility();
                PDDocument op_pdf = new PDDocument(); 
                  for(String pdfpage: pdfrange){
                    if(pdfpage.length() == 1){
                       int i= Integer.parseInt(pdfpage);
                       PDFmerger.appendDocument(op_pdf, Pages.get(i-1));

                    }
                    if(pdfpage.contains("-")){
                        int dashind= pdfpage.indexOf('-');
                        int startpage =Integer.parseInt(pdfpage.substring(0,dashind));
                        int endpage =Integer.parseInt(pdfpage.substring(dashind+1));
                        for(int i=startpage;i<=endpage;i++){
                            PDFmerger.appendDocument(op_pdf, Pages.get(i-1));
                        }
                    }
                }
                op_pdf.save(pdffile.getName()+"- "+ j++ + ".pdf");
            }
     
     }
}
    
    

