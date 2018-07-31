package sgh.mansilla.modelo.negocio.facturacion.Servicios;

import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import sgh.mansilla.modelo.datos.facturacion.Comprobante;
import sgh.mansilla.modelo.datos.facturacion.Concepto;
import sgh.mansilla.modelo.datos.facturacion.RespuestaAFIP;
import sgh.mansilla.modelo.datos.facturacion.RespuestaProcesarFactura;

public class FacturaToPDF {
    //Ejemplo path: C://Users//Juan Notebook//Desktop//UNLA//Pintureria//HTML to PDF//pdfs//testpdf.pdf
    //Ejemplo imagenFondo: C://Users//Juan Notebook//Desktop//UNLA//Pintureria//HTML to PDF//imagen.gif
    //Ejemplo codigoBarra, es la ubicacion donde se va a guardar dicho codigo: C:\\barCode donde barCode seria el nombre, va sin extension.
    public static Boolean ObtenerPDF(String path,String imagenFondo,String codigoBarra,Comprobante factura, String CUITEmisor,int cantidadCopias,boolean esFactura){
        boolean respuesta = false;
        for(int i=0;i<cantidadCopias;i++){
            String pathAux = path;
            String multiplicidad = getMultiplicidad(i+1);
            if(esFactura){
                pathAux = path.replace(".pdf","_"+multiplicidad+".pdf"); // le agrego al nombre al multiplicidad
            }
            else{
                pathAux = path.replace(".pdf","_REMITO_"+multiplicidad+".pdf"); // le agrego al nombre al multiplicidad
            }
            respuesta = ObtenerPDF(pathAux,imagenFondo,codigoBarra,factura,CUITEmisor,multiplicidad,esFactura);
            if(!respuesta){ //si alguna de las copias falla, salgo del bucle y devuelvo que no se creo el pdf
                break;
            }
        }
     return respuesta;
    }

    public static String getMultiplicidad(int numeroMultiplicidad){
        String multiplicidad = "";
        switch(numeroMultiplicidad){
            case 1:
                multiplicidad = "ORIGINAL";
                break;
            case 2:
                multiplicidad = "DUPLICADO";
                break;
            case 3:
                multiplicidad = "TRIPLICADO";
                break;
            case 4:
                multiplicidad = "CUADRUPLICADO";
                break;

        }
        return multiplicidad;

    }
    private static Boolean ObtenerPDF(String path,String imagenFondo,String codigoBarra, Comprobante factura,String CUITEmisor,String multiplicidad,boolean esFactura)
    {
        try {
            List<Concepto> articulos = factura.getConceptos();

            String letraFactura=factura.getTipoComprobante().getCodigo(),
                    numeroFactura=String.valueOf(factura.getNroComprobante()),
                    fechaFactura=factura.getFechaComprobante().toString(),
                    nombreCliente=factura.getClienteComprobante().getNombre()+" "+factura.getClienteComprobante().getApellido(),
                    domicilioCliente= "Calle Falsa 123",
                    localidadCliente= "Lanus",
                    condicionIvaCliente= factura.getClienteComprobante().getTipoDocumento().getDescripcion(),
                    condicionVenta=factura.getConceptosAIncluir().getDescripcion(),
                    cuitCliente=factura.getClienteComprobante().getDocumento(),
                    cae = "",
                    fechaVto = "",
                    subtotal=String.valueOf(factura.getImporteNeto()),
                    impuesto=String.valueOf(factura.getImporteTributos()),
                    porcentajeIva=String.valueOf(factura.getTotalIva()),
                    totalIva="",
                    total=String.valueOf(factura.getImporteTotal()),
                    discriminaIva="",htmlFacturaAsociada="";
            if(factura.getCae() != null){
                cae = factura.getCae();
                fechaVto = factura.getVencimientoCae().toGMTString();
            }

            if(factura.esFacturaA()) {
                discriminaIva="    <td width=\"291\">I.V.A Inscripto " + porcentajeIva +
                        "    %</td>";
                totalIva="    <td>"+String.valueOf(factura.getTotalIva())+"</td>";
            }
//            if(factura.getFacturaAsociada() != 0){
//                htmlFacturaAsociada = "<label> Factura asociada: "+factura.getFacturaAsociada()+"</label>";
//            }
            if(factura.getCae() !=null) {
                FileManager.generarCodigoDeBarra(CUITEmisor, factura, 002, codigoBarra);
            }
            Document document = new Document(PageSize.A4,60, 60, 50, 50);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.addAuthor("Sistema facturacion");
            document.addCreator("Sistema facturacion");
            document.addSubject("Thanks for your support");
            //document.setPageSize(PageSize.A4);
            //document.setMargins()

            document.addCreationDate();
            document.addTitle("Factura");

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

            String str =
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
                            "<head>"+
                            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta/>"+
                            "<title>Documento sin título</title>"+
                            "<style type=\"text/css\">"+
                            ".nombre {"+
                            "	font-family: Comic Sans MS, cursive;"+
                            "	font-size: 42px;"+
                            "	font-weight: bold;"+
                            "	color: #00C;"+
                            "}"+
                            ".nombre2 {"+
                            "	font-size: 24px;"+
                            "}"+
                            ".nombre3 {"+
                            "	font-size: 18px;"+
                            "	font-weight: bold;"+
                            "}"+
                            ".nombre5 {"+
                            "	font-size: 14px;"+
                            "	font-weight: bold;"+
                            "}"+
                            ".nombre6 {"+
                            "	font-size: 36px;"+
                            "	font-weight: bold;"+
                            "	font-style: italic;"+
                            "}"+
                            ".nombre4 {"+
                            "	font-weight: bold;"+
                            "}"+
                            ".nombre4 {"+
                            "	font-style: italic;"+
                            "}"+
                            ".codigoBarra {"+
                            "   padding-left:10px;"+
                            "   margin-left:10px"+
                            "   padding-top:10px;"+
                            "   margin-top:10px"+
                            "}"+
                            ".nombre4 {"+
                            "	font-weight: bold;"+
                            "	font-size: 24px;"+
                            "}"+
                            "encabezadoComun {"+
                            "	font-style: normal;"+
                            "}"+
                            "#form1 .nombre4 {"+
                            "	font-weight: normal;"+
                            "}"+
                            "#form1 .nombre4 {"+
                            "	font-style: normal;"+
                            "}"+
                            "</style>"+
                            "</head>"+

                            "<body>"+
                            "<table width=\"870\" align=\"center\" height=\"159\" border=\"4\">"+
                            "  <tr style=\"line-height: 85%;\">"+
                            "    <td width=\"424\">" +
                            "       <p align=\"center\" class=\"nombre3\">"+multiplicidad+"</p>"+
                            "       <p align=\"center\" class=\"nombre4\">Hotel </p>"+
                            "      <p align=\"center\" class=\"nombre\">Pipinas <span class=\"nombre2\">S.R.L</span></p>"+
                            "    <p align=\"center\">Calle Falsa 123</p>"+
                            "    <p align=\"center\"><span class=\"nombre4\">5555-5555</span> - Pcia. de Buenos Aires</p>"+
                            "    <p align=\"center\">Iva Responsable Inscripto</p></td>"+
                            "    <td width=\"424\"><form id=\"form1\" name=\"form1\" method=\"post\" action=\"\">";
                            if(esFactura){
                                str = str+ "    <p align=\"center\" class=\"nombre4\"><strong><em>"+factura.getTipoComprobante().getDescripcion()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</em></strong>"+
                                        "       <span id=\"nombre6\"><em><strong>&nbsp;"+letraFactura+"&nbsp;</strong></em>"+
                                        "       <strong></strong></span> </p>"+
                                        "      <p align=\"center\" class=\"nombre4\">N° 0001  - "+
                                        numeroFactura+
                                        "      </p>";
                            }
                            else{
                                str = str+ "  <p align=\"center\" class=\"nombre4\"> <strong><em>Remito&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</em></strong></p>";

                            }

                            str = str +"      <p align=\"center\" class=\"nombre4\"><em>Fecha: "+fechaFactura+"</em></p>"+
                            "      <p align=\"center\">C.U.I.T: 30-70865830-4</p>"+
                            "      <p align=\"center\">Ingresos Brutos: 30-70865830-4</p>"+
                            "      <p align=\"center\">Inicio de Actividades: 12/02/2004</p>"+
                            "    </form></td>"+
                            "  </tr>"+
                            "</table>"+
                            "<table width=\"870\" border=\"4\">"+
                            " <tr>"+
                            "    <td height=\"88\"><p>Señor(es): "+nombreCliente+"</p>"+
                            "    <p>Domicilio: "+domicilioCliente+"</p>"+
                            "    <p>Localidad: "+localidadCliente+"</p></td>"+
                            "  </tr>"+
                            "</table>"+
                            "<table width=\"870\" height=\"77\" border=\"4\">"+
                            "  <tr>"+
                            "    <td><p>I.V.A: "+condicionIvaCliente+"</p>"+
                            "    <p>Condicion de venta: "+condicionVenta+"</p></td>"+
                            "    <td><p>C.U.I.T: "+cuitCliente+"</p>"+
                            "    <p> </p></td>"+
                            "  </tr>"+
                            "</table>"+
                            "<table width=\"870\" border=\"4\" background=\""+imagenFondo+"\">"+
                            "  <tr>"+
                            "    <td width=\"106\"><div class=\"nombre5\" align=\"center\">CANTIDAD</div></td>"+
                            "    <td width=\"520\"><div class=\"nombre5\" align=\"center\">DESCRIPCION</div></td>";
                            if(esFactura){
                                str = str + "   <td width=\"100\"><div class=\"nombre5\" align=\"center\">PRECIO UNITARIO</div></td>"+
                                        "    <td width=\"110\"><div class=\"nombre5\" align=\"center\">IMPORTE</div></td>" ;
                            }
                            str = str + "  </tr>"+
                            ArticulosToString(articulos,esFactura) + "</table>";
                             if(esFactura){
                                str = str +
                                        "<table width=\"870\" border=\"4\">"+
                                        "  <tr>"+
                                        "    <td width=\"123\"><div align=\"center\">Sub-Total</div></td>"+
                                        "    <td width=\"128\"><div align=\"center\">Impuesto</div></td>"+
                                        "    <td width=\"139\"><div align=\"center\">Sub-Total</div></td>"+
                                        discriminaIva +
                                        "    <td width=\"149\"><div align=\"center\"><strong>TOTAL $</strong></div></td>"+
                                        "  </tr>"+
                                        "  <tr>"+
                                        "    <td>"+subtotal+"</td>"+
                                        "    <td>"+impuesto+"</td>"+
                                        "    <td>"+subtotal+"</td>"+
                                        totalIva+
                                        "    <td>"+total+"</td>"+
                                        "  </tr>"+
                                        "</table>"+
                                        "<table width=\"870\" border=\"4\">"+
                                        "  <tr>"+
                                        "    <td>"+
                                        "      <label>"+
                                        "          C.A.E: "+cae+"&nbsp;&nbsp;&nbsp;"+
                                        "        </label>"+
                                        "        <label>"+
                                        "          Fecha Vto: "+fechaVto+
                                        "        </label>"+
                                        htmlFacturaAsociada+"<br/>"+
                                        "<br/>" +
                                        "<br/>"+
                                        "        <div class=\"codigoBarra\"> <img hspace=\"2\" class=\"codigoBarra\" height=\"33\" width=\"330\" src=\""+codigoBarra+"\"></img> </div> "+
                                        "    <p>&nbsp; </p></td>"+
                                        "  </tr>"+
                                        "</table>";
                            }

                            str = str + "<p>&nbsp;</p>"+
                            "</body>"+
                            "</html>";

            worker.parseXHtml(pdfWriter, document, new StringReader(str));
//            PdfContentByte canvas = pdfWriter.getDirectContentUnder();
//            Image image = Image.getInstance(imagenFondo);
//            image.setAbsolutePosition(60, 275);
//            canvas.reset();
//            canvas.addImage(image);
            document.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String ArticulosToString(List<Concepto> articulos,boolean esFactura)
    {
        String articulosString="";

        for(Concepto art: articulos)
        {
            articulosString=articulosString+
                    "  <tr>"+
                    "    <td>"+art.getCantidad()+"</td>"+
                    "    <td>"+art.getDescripcion()+"</td>";
                    if(esFactura){
                        articulosString = articulosString + "    <td>"+art.getPrecio()+"</td>"+
                                "    <td>"+art.getPrecio()*art.getCantidad()+"</td>";
                    }
                    articulosString = articulosString + "  </tr>";


        }

        if(articulos.size()<13)
        {
            for(int i=articulos.size()-1;i<13;i++)
            {
                articulosString=articulosString+
                        "  <tr>"+
                        "    <td>&nbsp;</td>"+
                        "    <td>&nbsp;</td>";
                        if(esFactura) {
                            articulosString = articulosString + "    <td>&nbsp;</td>" +
                                    "    <td>&nbsp;</td>" ;
                        }
                        articulosString = articulosString +"  </tr>";
            }
        }

        return articulosString;

    }

}

