package imagezoomapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;




public class ImageZoomApp extends JFrame {

        private JLabel imageLabel;
        private JButton openButton;
        private JButton zoomInButton;
        private JButton zoomOutButton;
        private JButton resetButton;
        private JScrollPane scrollPane;
        private ImageIcon originalImageIcon; // Stores the original image
        private ImageIcon scaledImageIcon;   // Stores the scaled image
        private double scaleFactor = 1.0;    // Stores the current zoom scale factor

        
        public ImageZoomApp()
        {
          
            setTitle("IMAGE");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800,600);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            initialize(); // initialize the user interface
       
}
        private void initialize()
        {
            String image = null;
            imageLabel = new JLabel(image);
            scrollPane = new JScrollPane(imageLabel);
            
            UIManager.put("Button.background", new Color(52,152,219));
            UIManager.put("Button.foreground", Color.black);
            
            openButton = new JButton("Open Image");
            openButton.setFont(new Font("Arial", Font.BOLD, 14));
            openButton.addActionListener(new openButtonListener());
            
           
            
            zoomInButton = new JButton("zoom-In");
            zoomInButton.setFont(new Font("Arial", Font.BOLD, 14));
            zoomInButton.addActionListener(new zoomInButtonListener());
            
            
            zoomOutButton = new JButton("zoom-Out");
            zoomOutButton.setFont(new Font("Arial", Font.BOLD, 14));
            zoomOutButton.addActionListener(new zoomOutButtonListener());
            
            
            resetButton = new JButton("Reset");
            resetButton.setFont(new Font("blue", Font.BOLD, 14));
            resetButton.addActionListener(new resetButtonListener());
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.black);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            buttonPanel.add(openButton);
            buttonPanel.add(zoomInButton);
            buttonPanel.add(zoomOutButton);
            buttonPanel.add(resetButton);
            
            add(scrollPane, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
            
        
        }



    
 // Create a Listener for the "Open Image" button
        private class openButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
            
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(ImageZoomApp.this);
                if(result == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        File selectedFile = fileChooser.getSelectedFile();
                        originalImageIcon = new ImageIcon(ImageIO.read(selectedFile));
                        displayImage();
                        
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(ImageZoomApp.this, "Error Opening the selected image", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            
            }
            
        }
        
              
        // Create a Listener for the "Zoom In" button
        private class zoomInButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
            
                try
                {
                    scaleFactor *= 1.2;
                    displayImage();
                }
                catch(OutOfMemoryError error)
                {
                    JOptionPane.showMessageDialog(ImageZoomApp.this, "Maximum zoom level reached.", "Zoom Limit", JOptionPane.ERROR_MESSAGE); 
                }
              
            }
            
        }
        
        
       // Create a Listener for the "Zoom Out" button
        private class zoomOutButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
            
                try
                {
                    scaleFactor /= 1.2;
                    displayImage();
                }
                catch(Exception ex){ 
                    JOptionPane.showMessageDialog(ImageZoomApp.this, "No More Zoom Out", "Error", JOptionPane.ERROR_MESSAGE); 
                }
                
            
            }
            
        } 
      
           // Create a Listener for the "Reset" button
        private class resetButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
            
                scaleFactor = 2.0;
                displayImage();
            
            }
            
        }
        
        
        
        
    // Create a Function to Update the displayed image based on the current scale factor
        private void displayImage()
        {
            if(originalImageIcon != null)
            {
                int newWidth = (int) (originalImageIcon.getIconWidth()*scaleFactor);
                int newHeight = (int) (originalImageIcon.getIconHeight()*scaleFactor);
                
                scaledImageIcon = new ImageIcon(originalImageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
                
                imageLabel.setIcon(scaledImageIcon);
                scrollPane.setViewportView(imageLabel);
            }   
        }
        
        
        
  public static void main(String[] args) {
        
       SwingUtilities.invokeLater(()->{
       
           ImageZoomApp app = new ImageZoomApp();
           app.setVisible(true);
         //  app.frmLogin.setVisible(true);
       });
        
    }

}
