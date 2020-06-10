import com.github.sarxos.webcam.Webcam;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Recognizer extends JFrame {

    public static JFrame jFrame;
    public static JPanel panel1;
    public  static JLabel plateNumberLabel;
    public  static JLabel plateNumber;
    public static JLabel vehicleTypeLabel;
    public static JLabel vehicleType;
    public static JPanel panel2;
    public static JSplitPane sp;

    public static void main(String[] args) throws IOException{
        // event handle and info display//
        jFrame = new JFrame("Park@Ease");


        jFrame.addKeyListener(new MKeyListener() );

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.setBackground(Color.decode("#bbd6f2"));
        plateNumberLabel=new JLabel("License Plate Number:");
        plateNumberLabel.setFont(new Font("Serif", Font.BOLD, 22));
        plateNumberLabel.setForeground(Color.decode("#0b3b6e"));
        plateNumber=new JLabel();

        vehicleTypeLabel=new JLabel("Vehicle Type:");
        vehicleTypeLabel.setFont(new Font("Serif", Font.BOLD, 22));
        vehicleTypeLabel.setForeground(Color.decode("#0b3b6e"));
        vehicleType=new JLabel();

        panel1.add(plateNumberLabel);
        panel1.add(plateNumber);
        panel1.add(vehicleTypeLabel);
        panel1.add(vehicleType);


        panel2 = new JPanel();
        panel2.setBackground(Color.decode("#bbd6f2"));

        sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.setResizeWeight(0.5);
        sp.setEnabled(false);
        sp.setDividerSize(5);


        sp.add(panel2);
        sp.add(panel1);
        jFrame.add(sp, BorderLayout.CENTER);
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setLocation(500,100);



    }

    public static void populateJFrame(String pn,String vt) throws IOException{
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.setBackground(Color.decode("#bbd6f2"));
        plateNumberLabel=new JLabel("License Plate Number:");
        plateNumberLabel.setFont(new Font("Serif", Font.BOLD, 22));
        plateNumberLabel.setForeground(Color.decode("#0b3b6e"));
        plateNumber=new JLabel(pn);
        plateNumber.setFont(new Font("Serif", Font.PLAIN, 22));

        vehicleTypeLabel=new JLabel("Vehicle Type:");
        vehicleTypeLabel.setFont(new Font("Serif", Font.BOLD, 22));
        vehicleTypeLabel.setForeground(Color.decode("#0b3b6e"));
        vehicleType=new JLabel(vt);
        vehicleType.setFont(new Font("Serif", Font.PLAIN, 22));

        panel1.add(plateNumberLabel);
        panel1.add(plateNumber);
        panel1.add(vehicleTypeLabel);
        panel1.add(vehicleType);


        panel2 = new JPanel();
        panel2.setBackground(Color.decode("#bbd6f2"));

        BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\Sameen\\Desktop\\22.jpg"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setSize(298,244);
        Image dimg = myPicture.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        picLabel.setIcon(imageIcon);

        panel2.add(picLabel);
        panel2.setVisible(true);

        sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.setResizeWeight(0.5);
        sp.setEnabled(false);
        sp.setDividerSize(5);

        sp.add(panel2);
        sp.add(panel1);
        jFrame.add(sp, BorderLayout.CENTER);
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
        jFrame.setLocation(500,100);
    }

    static class MKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event){

                if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                    try {
                        getImage();
                        String result=JSONParser(numberPlateDigitization());
                        String[] arr = result.split(",");
                        populateJFrame(arr[0],arr[1]);
                    } catch (Exception e) {

                    }
                }
        }

        public void getImage() throws IOException {
            // Image capturing//
            Webcam webcam=Webcam.getDefault();
            webcam.open();
            ImageIO.write(webcam.getImage(),"JPG",new File("firstCapture.jpg"));
            webcam.close();
        }

        public String numberPlateDigitization(){
            //API Call//
            String token = "81edc6948e107234a051807144b8659816e56cea";
            String file = "C:\\Users\\Sameen\\Desktop\\22.jpg";

            try{
                kong.unirest.HttpResponse<String> response = Unirest.post("https://api.platerecognizer.com/v1/plate-reader/")
                        .header("Authorization", "Token "+token)
                        .field("upload", new File(file))
                        .asString();
                return ((kong.unirest.HttpResponse) response).getBody().toString();
            }
            catch(Exception e){
                System.out.println(e);
            }

            try{
                HttpResponse<String> response = Unirest.get("https://api.platerecognizer.com/v1/statistics/")
                        .header("Authorization", "Token "+token)
                        .asString();
                return ((kong.unirest.HttpResponse) response).getBody().toString();
            }
            catch(Exception e){
                System.out.println(e);
            }
            return null;

        }

        public String JSONParser(String jsonResult){
            JSONObject jsonObj = new JSONObject(jsonResult.toString());
            JSONArray resultJsonArray = jsonObj.getJSONArray("results");
            String plateNumber=resultJsonArray.getJSONObject(0).getString("plate");
            JSONObject vehicle=resultJsonArray.getJSONObject(0).getJSONObject("vehicle");
            String vehicleType=vehicle.getString("type");
            return plateNumber+","+vehicleType;
        }
    }
}
