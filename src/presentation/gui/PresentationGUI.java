/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.gui;

//import javafx.scene.media.*;
import javax.sound.sampled.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Michael
 */

//import org.apache.derby.jdbc.*;
//import sun.jdbc.odbc.JdbcOdbcDriver;

public class PresentationGUI extends JFrame implements ActionListener, ItemListener
{
    //DECLARE THE ELEMENTS OR OBJECTS THAT YOU WILL PUT IN YOUR FRAME
    //NOTICE HOW A PANEL IS CREATED FOR EACH ONE THIS WILL MAKE IT EASIER BUILD
   
    public JLabel teamLabel;
    private JComboBox teamComboBox;
    public JComboBox imagesComboBox;
    private JPanel teamPanel;
    private JPanel q1Panel;
    private JLabel question1;
    private JSlider q1Slider;
    private Hashtable grades;
    private JPanel q2Panel;
    private JLabel question2;
    private JSlider q2Slider;
    private JPanel q3Panel;
    private JLabel question3;
    private JSlider q3Slider;
    private JPanel q4Panel;
    private JLabel question4;
    private JSlider q4Slider;
    private JPanel commentPanel;
    private JLabel commentLabel;
    private JTextArea commentTextArea;
    private JPanel calcAvgPanel;
    private JLabel computedLabel;
    private JTextArea avgTextArea;
    private JButton calcAvgButton;
    private JPanel buttonPanel;
    private JButton submitButton;
    private JButton clearButton;
   
   //these are fields that will be used to hold the values pulled from the interface
    int q1;
    int q2;
    int q3;
    int q4;
    String comments;
    double teamAvg;
        
    // instance variables used to manipulate database
   private Connection con;
   private Statement stmt;
   private ResultSet rs;
           
   //MAIN METHOD: NOTICE WE TAKE IN THE ARGUMENTS THAT ARE
   //PASSED IN AND INSTANTIATE OUR CLASS WITH THEM
    public static void main(String args[])
    {
        String databaseDriver = "org.apache.derby.jdbc.ClientDriver";
        //String databaseDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
//          String databaseURL = "jdbc:derby://localhost:1527/Evaluation2";
          String databaseURL = "jdbc:derby://localhost:1527/Evaluation2;create=true";

         // create new PresentationGUI
         PresentationGUI eval = new PresentationGUI(databaseDriver, databaseURL);
         eval.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   
    //CONSTRUCTOR: WE SET UP OUR DATABASE HERE THEN MAKE A CALL
    //TO A FUNCTION CALLED CREATEUSERINTERFACE TO BUILD OUR GUI
    public PresentationGUI(String databaseDriver, String databaseURL)
    {
        // establish connection to database
      try
      {
         // load Sun driver
         //Class.forName( databaseDriver );
           DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
         // connect to database
           //con = DriverManager.getConnection(databaseURL);
           con = DriverManager.getConnection(databaseURL, "user1", "1111");

         // create Statement for executing SQL
         stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      }
      catch (SQLException ex)
      {
         ex.printStackTrace();
      }
            
     createUserInterface(); // set up GUI     
    }
   
    private void createUserInterface()
    {
      // get content pane for attaching GUI components
      Container contentPane = getContentPane();       
      contentPane.setLayout(null);
      
      // INSTRUCTOR COMBO BOX SET UP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      // set up Instructor Panel
      
      // enable explicit positioning of GUI components
      
      teamPanel = new JPanel();
      teamPanel.setBounds(40, 20, 400, 48 );
      teamPanel.setLayout(null);
      contentPane.add(teamPanel);

      // set up Instructor Label
      teamLabel = new JLabel();
      teamLabel.setBounds(25, 15, 100, 20);
      teamLabel.setText("TEAMS");
      teamPanel.add(teamLabel);

      // set up accountNumberJComboBox
      teamComboBox = new JComboBox();
      teamComboBox.setBounds(130, 15, 265, 25);
      teamComboBox.addItem("");
      teamComboBox.setSelectedIndex(0);
      teamPanel.add(teamComboBox); 
      contentPane.add(teamPanel);
      
      buildDictionary();
      
      q1Panel = new JPanel();
      q1Panel.setBounds(40, 70, 400, 60);
      q1Panel.setLayout(null);
            
      question1 = new JLabel();
      question1.setBounds(25, 15, 100, 20);
      question1.setText("Q1: Techincal ?");
      q1Panel.add(question1);
            
      q1Slider = new JSlider(JSlider.HORIZONTAL, 1, 8, 5);
      q1Slider.setBounds(120, 15, 275, 40);
      q1Slider.setMinorTickSpacing(1);
      q1Slider.setPaintTicks(true);
      q1Slider.setLabelTable(grades);
      q1Slider.setPaintLabels(true);
      q1Slider.setInverted(true);
      q1Panel.add(q1Slider);
      contentPane.add(q1Panel);
      
      q2Panel = new JPanel();
      q2Panel.setBounds(40, 130, 400, 60);
      q2Panel.setLayout(null);
      
      question2 = new JLabel();
      question2.setBounds(25, 15, 100, 20);
      question2.setText("Q2: Useful ?");
      q2Panel.add(question2);
            
      q2Slider = new JSlider(JSlider.HORIZONTAL, 1, 8, 5);
      q2Slider.setBounds(120, 15, 275, 40);
      q2Slider.setMinorTickSpacing(1);
      q2Slider.setPaintTicks(true);
      q2Slider.setLabelTable(grades);
      q2Slider.setPaintLabels(true);
      q2Slider.setInverted(true);
      q2Panel.add(q2Slider);
      contentPane.add(q2Panel);
      
      q3Panel = new JPanel();
      q3Panel.setBounds(40, 190, 400, 60);
      q3Panel.setLayout(null);
      
      question3 = new JLabel();
      question3.setBounds(25, 15, 100, 20);
      question3.setText("Q3: Clarity ?");
      q3Panel.add(question3);
            
      q3Slider = new JSlider(JSlider.HORIZONTAL, 1, 8, 5);
      q3Slider.setBounds(120, 15, 275, 40);
      q3Slider.setMinorTickSpacing(1);
      q3Slider.setPaintTicks(true);
      q3Slider.setLabelTable(grades);
      q3Slider.setPaintLabels(true);
      q3Slider.setInverted(true);
      q3Panel.add(q3Slider);
      contentPane.add(q3Panel);
      
      q4Panel = new JPanel();
      q4Panel.setBounds(40, 250, 400, 60);
      q4Panel.setLayout(null);
      
      question4 = new JLabel();
      question4.setBounds(25, 15, 100, 20);
      question4.setText("Q4: Overall ?");
      q4Panel.add(question4);
            
      q4Slider = new JSlider(JSlider.HORIZONTAL, 1, 8, 5);
      q4Slider.setBounds(120, 15, 275, 40);
      q4Slider.setMinorTickSpacing(1);
      q4Slider.setPaintTicks(true);
      q4Slider.setLabelTable(grades);
      q4Slider.setPaintLabels(true);
      q4Slider.setInverted(true);
      q4Panel.add(q4Slider);
      contentPane.add(q4Panel);
      
      commentPanel = new JPanel();
      commentPanel.setBounds(40, 310, 400, 80);
      commentPanel.setLayout(null);
      
      commentLabel = new JLabel();
      commentLabel.setBounds(25, 15, 100, 20);
      commentLabel.setText("Comments:");
      commentPanel.add(commentLabel);
      
      commentTextArea = new JTextArea("\nAdd Group Member Names Here\n");
      commentTextArea.setBounds(120, 15, 275, 50);
      commentPanel.add(commentTextArea);
      contentPane.add(commentPanel);
      
      calcAvgPanel = new JPanel();
      calcAvgPanel.setBounds(40, 390, 400, 80);
      calcAvgPanel.setLayout(null);
      
      computedLabel = new JLabel();
      computedLabel.setBounds(25, 15, 250, 20);
      computedLabel.setText("Computed Average from questions above:");
      calcAvgPanel.add(computedLabel);
      
      avgTextArea = new JTextArea();
      avgTextArea.setBounds(270, 15, 120, 55);
      calcAvgPanel.add(avgTextArea);
      
      calcAvgButton = new JButton("Calc Avg");
      calcAvgButton.setBounds(25, 40, 85, 30);
      calcAvgPanel.add(calcAvgButton);      
      contentPane.add(calcAvgPanel);
      
      buttonPanel = new JPanel();
      buttonPanel.setBounds(40, 510, 400, 40);
      buttonPanel.setLayout(null);
      
      submitButton = new JButton("Submit");
      submitButton.setBounds(40, 5, 150, 25);
      submitButton.setEnabled(false);
      buttonPanel.add(submitButton);
      
      clearButton = new JButton("Clear");
      clearButton.setBounds(240, 5, 150, 25);
      buttonPanel.add(clearButton);
      contentPane.add(buttonPanel);
      
      calcAvgButton.addActionListener(this);
      submitButton.addActionListener(this);
      clearButton.addActionListener(this);
      teamComboBox.addItemListener(this);
      
      loadTeams();
            
      setTitle("EEEVILL");   // set title bar string
      setSize(500, 600); // set window size
      setVisible(true);  // display window
      
      try
      {
        AudioInputStream AIS = AudioSystem.getAudioInputStream(new File("E:\\Object Programming 2\\Projects\\Project 2\\Presentation GUI\\EVIL!!!.wav").getAbsoluteFile());
        Clip mermaidMan = AudioSystem.getClip();
        mermaidMan.open(AIS);
        mermaidMan.start();        
      } 
      catch(Exception ex) 
      {
        System.out.println("Error with playing sound.");
        ex.printStackTrace();
      }
    }

   
    private void loadTeams()
    {
         // get all account numbers from database
      try
      {       
         rs = stmt.executeQuery("SELECT DISTINCT TEAMNAME FROM EVALUATIONS");      
         while (rs.next())
         {
             teamComboBox.addItem(rs.getString("TEAMNAME"));
         }
         rs = stmt.executeQuery("select * from EVALUATIONS"); //or else it won't see any other rows    
      } // end try

      catch (SQLException exception)
      {
         exception.printStackTrace();
      }
    }

    @Override
   public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == calcAvgButton)
        {
            submitButton.setEnabled(true);
            q1 = q1Slider.getValue();
            q2 = q2Slider.getValue();
            q3 = q3Slider.getValue();
            q4 = q4Slider.getValue();
            teamAvg = (double) (q1 + q2 + q3 + q4)/4;
            String avgString = Double.toString(teamAvg);
            avgTextArea.setText(avgString + " out of 8.0");
        }        
        else if(event.getSource() == submitButton)
        {
            submitButton.setEnabled(false);            
            comments = commentTextArea.getText();
            try
            {
                rs.updateInt("Q1", q1);
                rs.updateInt("Q2", q2);
                rs.updateInt("Q3", q3);
                rs.updateInt("Q4", q4);
                rs.updateDouble("AVGSCORE", teamAvg);
                rs.updateString("COMMENTS", comments);
                rs.updateRow();

                AudioInputStream AIS = AudioSystem.getAudioInputStream(new File("E:\\Object Programming 2\\Projects\\Project 2\\Presentation GUI\\Complete.wav").getAbsoluteFile());
                Clip complete = AudioSystem.getClip();
                complete.open(AIS);
                complete.start();        
            }
            catch(SQLException | RuntimeException ex)
            {
                ex.printStackTrace();               
                try
                {
                    AudioInputStream AIS = AudioSystem.getAudioInputStream(new File("E:\\Object Programming 2\\Projects\\Project 2\\Presentation GUI\\Failure.wav").getAbsoluteFile());
                    Clip failure = AudioSystem.getClip();
                    failure.open(AIS);
                    failure.start();        
                } 
                catch(Exception exc) 
                {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
            }
            catch(Exception ex) 
            {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
        else if(event.getSource() == clearButton)
        {
            submitButton.setEnabled(false);
                    
            q1Slider.setValue(5);
            q2Slider.setValue(5);
            q3Slider.setValue(5);
            q4Slider.setValue(5);              
            avgTextArea.setText("");
            commentTextArea.setText("\nAdd Group Member Names Here\n");
        }
    }

    @Override
   public void itemStateChanged(ItemEvent event)
   {
        if(event.getStateChange() == ItemEvent.SELECTED)
        {
             try
             {
                 rs.absolute(teamComboBox.getSelectedIndex());
                 if(rs.getInt("Q1")> 0)
                 {
                    q1Slider.setValue(rs.getInt("Q1"));
                    q2Slider.setValue(rs.getInt("Q2"));
                    q3Slider.setValue(rs.getInt("Q3"));
                    q4Slider.setValue(rs.getInt("Q4"));
                    String avgScore = Double.toString(rs.getDouble("AVGSCORE"));
                    avgTextArea.setText(avgScore + " out of 8.0");
                    commentTextArea.setText(rs.getString("COMMENTS"));
                 }
                 else
                 {
                    q1Slider.setValue(5);
                    q2Slider.setValue(5);
                    q3Slider.setValue(5);
                    q4Slider.setValue(5);              
                    avgTextArea.setText("");
                    commentTextArea.setText("\nAdd Group Member Names Here\n");
                 }
             }
             catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }
   }
   
   public void buildDictionary()
   {
       grades = new Hashtable();
       grades.put(1, new JLabel("C-"));
       grades.put(2, new JLabel("C"));
       grades.put(3, new JLabel("C+"));       
       grades.put(4, new JLabel("B-"));
       grades.put(5, new JLabel("B"));
       grades.put(6, new JLabel("B+"));   
       grades.put(7, new JLabel("A-"));       
       grades.put(8, new JLabel("A"));
   }
}