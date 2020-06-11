import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
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

public class Recognizer extends JFrame {

    public JFrame jFrame;
    public JPanel panel1;
    public JLabel plateNumberLabel;
    public JLabel plateNumber;
    public JLabel vehicleTypeLabel;
    public JLabel vehicleType;
    public JLabel lastParkedVehicleLabel;
    public JLabel lastParkedVehicle;
    public JLabel statusLabel;
    public JLabel status;
    public JLabel extraLabel;
    public JLabel extra;
    public JPanel panel2;
    public JSplitPane sp1;
    public JLabel videoLabel;
    public JLabel picLabel;
    public JSplitPane sp2;
    public JPanel panel3;
    Webcam webcam;


    public Recognizer(){
        initGUI();
        webcam=Webcam.getDefault();
        webcam.open();
        new VideoFeedTracker().start();

    }

    public static void main(String[] args) throws IOException{
        Recognizer recognizer=new Recognizer();
    }

    public void initGUI(){
        // event handle and info display//
        jFrame = new JFrame("Park@Ease");

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.setBackground(Color.decode("#bbd6f2"));

        plateNumberLabel=new JLabel("License Plate Number:");
        plateNumberLabel.setFont(new Font("Serif", Font.BOLD, 22));
        plateNumberLabel.setForeground(Color.decode("#0b3b6e"));
        plateNumber=new JLabel("");

        vehicleTypeLabel=new JLabel("Vehicle Type:");
        vehicleTypeLabel.setFont(new Font("Serif", Font.BOLD, 22));
        vehicleTypeLabel.setForeground(Color.decode("#0b3b6e"));
        vehicleType=new JLabel("");

        lastParkedVehicleLabel=new JLabel("Last Parked Vehicle:");
        lastParkedVehicleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        lastParkedVehicleLabel.setForeground(Color.decode("#0b3b6e"));
        lastParkedVehicle=new JLabel("");

        statusLabel=new JLabel("Status:");
        statusLabel.setFont(new Font("Serif", Font.BOLD, 22));
        statusLabel.setForeground(Color.decode("#0b3b6e"));
        status=new JLabel("");

        extraLabel=new JLabel("Extra:");
        extraLabel.setFont(new Font("Serif", Font.BOLD, 22));
        extraLabel.setForeground(Color.decode("#0b3b6e"));
        extra=new JLabel("");

        panel1.add(plateNumberLabel);
        panel1.add(plateNumber);
        panel1.add(vehicleTypeLabel);
        panel1.add(vehicleType);
        panel1.add(lastParkedVehicleLabel);
        panel1.add(lastParkedVehicle);
        panel1.add(statusLabel);
        panel1.add(status);
        panel1.add(extraLabel);
        panel1.add(extra);


        panel3=new JPanel();
        panel3.setBackground(Color.decode("#bbd6f2"));
        picLabel=new JLabel();
        panel3.add(picLabel);

        sp2= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp2.setResizeWeight(0.7);
        sp2.setEnabled(false);
        sp2.setDividerSize(5);

        sp2.add(panel1);
        sp2.add(panel3);


        panel2 = new JPanel();
        panel2.setBackground(Color.decode("#bbd6f2"));
        videoLabel =new JLabel();
        videoLabel.setSize(600,300);

        sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp1.setResizeWeight(0.1);
        sp1.setEnabled(false);
        sp1.setDividerSize(5);


        sp1.add(panel2);
        sp1.add(sp2);
        jFrame.add(sp1, BorderLayout.CENTER);
        jFrame.setSize(800, 700);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.addKeyListener(new MKeyListener() );
        jFrame.setLocationRelativeTo(null);
    }

    // change the parameters of this function according to the labels you gonna use...currently using dummy data for extra labels
    public void populateJFrame(String pn,String vt,Image image) throws IOException{
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.setBackground(Color.decode("#bbd6f2"));
        plateNumberLabel=new JLabel("License Plate Number:");
        plateNumberLabel.setFont(new Font("Serif", Font.BOLD, 22));
        plateNumberLabel.setForeground(Color.decode("#0b3b6e"));
        plateNumber.setText(pn);
        panel1.add(plateNumber);
        plateNumber.setFont(new Font("Serif", Font.PLAIN, 22));

        vehicleTypeLabel=new JLabel("Vehicle Type:");
        vehicleTypeLabel.setFont(new Font("Serif", Font.BOLD, 22));
        vehicleTypeLabel.setForeground(Color.decode("#0b3b6e"));
        vehicleType=new JLabel(vt);
        vehicleType.setFont(new Font("Serif", Font.PLAIN, 22));

        lastParkedVehicleLabel=new JLabel("Last Parked Vehicle:");
        lastParkedVehicleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        lastParkedVehicleLabel.setForeground(Color.decode("#0b3b6e"));
        lastParkedVehicle=new JLabel("c");
        lastParkedVehicle.setFont(new Font("Serif", Font.PLAIN, 22));

        statusLabel=new JLabel("Status:");
        statusLabel.setFont(new Font("Serif", Font.BOLD, 22));
        statusLabel.setForeground(Color.decode("#0b3b6e"));
        status=new JLabel("d");
        status.setFont(new Font("Serif", Font.PLAIN, 22));

        extraLabel=new JLabel("Extra:");
        extraLabel.setFont(new Font("Serif", Font.BOLD, 22));
        extraLabel.setForeground(Color.decode("#0b3b6e"));
        extra=new JLabel("e");
        extra.setFont(new Font("Serif", Font.PLAIN, 22));

        panel1.add(plateNumberLabel);
        panel1.add(plateNumber);
        panel1.add(vehicleTypeLabel);
        panel1.add(vehicleType);
        panel1.add(lastParkedVehicleLabel);
        panel1.add(lastParkedVehicle);
        panel1.add(statusLabel);
        panel1.add(status);
        panel1.add(extraLabel);
        panel1.add(extra);
        panel1.setVisible(true);


        panel3=new JPanel();
        panel3.setBackground(Color.decode("#bbd6f2"));
        picLabel=new JLabel();
        picLabel.setSize(250,300);
        Image dimg = image.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        picLabel.setIcon(imageIcon);

        panel3.add(picLabel);
        panel3.setVisible(true);


        sp2= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sp2.setResizeWeight(0.9);
        sp2.setEnabled(false);
        sp2.setDividerSize(5);

        sp2.add(panel1);
        sp2.add(panel3);

        panel2 = new JPanel();
        panel2.setBackground(Color.decode("#bbd6f2"));

        sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp1.setResizeWeight(0.1);
        sp1.setEnabled(false);
        sp1.setDividerSize(5);

        sp1.add(panel2);
        sp1.add(sp2);
        jFrame.add(sp1, BorderLayout.CENTER);
        jFrame.setSize(800, 700);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

    public void getImage() throws IOException {
        // Image capturing//
        ImageIO.write(webcam.getImage(),"JPG",new File("firstCapture.jpg"));
    }

    class MKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event){

            if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getImage();
                    String result=JSONParser(numberPlateDigitization());
                    String[] arr = result.split(",");
                    populateJFrame(arr[0],arr[1], new ImageIcon("C:\\Users\\Sameen\\IdeaProjects\\untitled2\\firstCapture.jpg").getImage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
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

    public  String JSONParser(String jsonResult) {
        JSONObject jsonObj = new JSONObject(jsonResult.toString());
        JSONArray resultJsonArray = jsonObj.getJSONArray("results");
        String plateNumber = resultJsonArray.getJSONObject(0).getString("plate");
        JSONObject vehicle = resultJsonArray.getJSONObject(0).getJSONObject("vehicle");
        String vehicleType = vehicle.getString("type");
        return plateNumber + "," + vehicleType;
    }

    class VideoFeedTracker extends Thread{

        @Override
        public void run(){
            while(true){
                try{
                Image image=webcam.getImage();
                videoLabel.setSize(600,300);
                Image dimg = image.getScaledInstance(videoLabel.getWidth(), videoLabel.getHeight(),
                        Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                videoLabel.setIcon(imageIcon);
                panel2.add(videoLabel);
                panel2.setVisible(true);
                sp1.add(panel2);
                Thread.sleep(40);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
