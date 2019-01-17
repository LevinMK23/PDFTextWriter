import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

public class PDFEditor {
    public static void main(String[] args) throws Exception {

        PdfReader reader = new PdfReader("/Users/levinMK/IdeaProjects/" +
                "ImagePrinter/src/file.pdf"); // input PDF
        File names = new File("/Users/levinMK/IdeaProjects/" +
                "ImagePrinter/src/names.txt");
        Scanner fin = new Scanner(names);
        ArrayList<String> listNames = new ArrayList<>();
        while (fin.hasNextLine()){
            listNames.add(fin.nextLine());
        }
        ArrayList<InputStream> streams = new ArrayList<>();
        for (int j = 0; j < listNames.size(); j++){
            String text = listNames.get(j);
            streams.add(new FileInputStream("/Users/levinMK/IdeaProjects/" +
                    "ImagePrinter/src/output/file_output" + (j+1) + ".pdf"));
            PdfStamper stamper = new PdfStamper(reader,
                    new FileOutputStream("/Users/levinMK/IdeaProjects/" +
                            "ImagePrinter/src/output/file_output" + (j+1) + ".pdf")); // output PDF
            BaseFont bf = BaseFont.createFont(
                    "/Users/levinMK/IdeaProjects/ImagePrinter/src/tahoma/TAHOMA_0.TTF",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); // set font

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                PdfContentByte over = stamper.getOverContent(i);

                over.beginText();
                over.setFontAndSize(bf, 30);    // set font and size
                over.setTextMatrix(130, 400);   // set x,y position (0,0 is at the bottom left)
                over.showText(text);  // set text
                over.endText();

//            over.setRGBColorStroke(0xFF, 0x00, 0x00);
//            over.setLineWidth(5f);
//            over.ellipse(250, 450, 350, 550);
//            over.stroke();
            }
            stamper.close();
            reader = new PdfReader("/Users/levinMK/IdeaProjects/" +
                    "ImagePrinter/src/file.pdf");
        }
        PDFMerger merger = new PDFMerger();
        merger.mergePdfFiles(streams, new FileOutputStream("/Users/levinMK/IdeaProjects/ImagePrinter/src/result.pdf"));
    }
}
