
package edu.se459grp4.project.gui;
import edu.se459grp4.project.cleansweep.CleanSweepManager;
import edu.se459grp4.project.simulator.Simulator;
import edu.se459grp4.project.simulator.models.Door;
import edu.se459grp4.project.simulator.models.Tile;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.MNEMONIC_KEY;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {

    private  FloorPlanPanel mFloorplanPanel;
    public MainFrame() {

        initUI();
    }

    private void initUI() {
        createMenuBar();
        mFloorplanPanel = new FloorPlanPanel();
        add(mFloorplanPanel);
        
        setTitle("CleanSweep Simulator");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();

        ImageIcon iconNew = new ImageIcon("new.png");
        ImageIcon iconOpen = new ImageIcon("open.png");
        ImageIcon iconSave = new ImageIcon("save.png");
        ImageIcon iconExit = new ImageIcon("exit.png");

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newMi = new JMenuItem(new MenuItemAction("LoadFloorPlan",
                iconNew,
                KeyEvent.VK_N,
                this));

        JMenuItem openMi = new JMenuItem(new MenuItemAction("SaveFloorPlan", 
                iconOpen,
                KeyEvent.VK_O,
                this));
        JMenuItem addChargeStationMi = new JMenuItem(new MenuItemAction("AddChargeStation", 
                iconOpen,
                KeyEvent.VK_T,
                this));
        JMenuItem removeChargeStationMi = new JMenuItem(new MenuItemAction("RemoveChargeStation", 
                iconOpen,
                KeyEvent.VK_L,
                this));
        
        JMenuItem addCleansweepMi = new JMenuItem(new MenuItemAction("AddCleanSweep", 
                iconOpen,
                KeyEvent.VK_Q,
                this));
        JMenuItem removeCleansweepMi = new JMenuItem(new MenuItemAction("RemoveCleanSweep", 
                iconOpen,
                KeyEvent.VK_P,
                this));
        JMenuItem openDoorMi = new JMenuItem(new MenuItemAction("OpenDoor", 
                iconOpen,
                KeyEvent.VK_Q,
                this));
        JMenuItem closeDoorMi = new JMenuItem(new MenuItemAction("CloseDoor", 
                iconOpen,
                KeyEvent.VK_P,
                this));
        
        fileMenu.add(newMi);
        fileMenu.add(openMi);
        fileMenu.add(addChargeStationMi);
        fileMenu.add(removeChargeStationMi);
        fileMenu.add(addCleansweepMi);
        fileMenu.add(removeCleansweepMi);
        fileMenu.add(openDoorMi);
        fileMenu.add(closeDoorMi);
        JMenu CleanSweepMenu = new JMenu("CleanSweep");
        CleanSweepMenu.setMnemonic(KeyEvent.VK_C);

        JMenuItem StartMi = new JMenuItem(new MenuItemAction("Start",
                iconNew,
                KeyEvent.VK_A,
                this));

        JMenuItem StopMi = new JMenuItem(new MenuItemAction("Stop", 
                iconOpen,
                KeyEvent.VK_B,
                this));
        
        JMenuItem ShowMi = new JMenuItem(new MenuItemAction("Status", 
                iconOpen,
                KeyEvent.VK_S,
                this));
        
        CleanSweepMenu.add(StartMi);
        CleanSweepMenu.add(StopMi);
        CleanSweepMenu.add(ShowMi);


        menubar.add(fileMenu);
        menubar.add(CleanSweepMenu);
        setJMenuBar(menubar);
    }


    private class MenuItemAction extends AbstractAction {
        private JFrame mFrame;
        public MenuItemAction(String text,
                ImageIcon icon,
                Integer mnemonic,
                JFrame nFrame) {
            super(text);
            putValue(SMALL_ICON, icon);
            putValue(MNEMONIC_KEY, mnemonic);
            mFrame = nFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println(e.getActionCommand());
            if(e.getActionCommand() == "LoadFloorPlan")
            {
                JFileChooser fileChooser = new JFileChooser();
                 int returnValue = fileChooser.showOpenDialog(null);
                 if (returnValue == JFileChooser.APPROVE_OPTION) {
                     File selectedFile = fileChooser.getSelectedFile();
                     //System.out.println(selectedFile.getName());
                     mFloorplanPanel.SetFloorPlan( Simulator.getInstance().loadFloorPlan(selectedFile.getPath()));
                 }
            }
            else if(e.getActionCommand() == "OpenDoor" || e.getActionCommand() == "CloseDoor" )
            {
                List<Door> lDoors = Simulator.getInstance().GetAllDoors();
                boolean lbOpen = e.getActionCommand() == "OpenDoor" ? true : false;
                Door lDoor = (Door)JOptionPane.showInputDialog(
                                    mFrame,
                                    "Please choose a door for the list:\n",
                                    e.getActionCommand() == "OpenDoor" ?"Choose a door to open":"Choose a door to close",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    lDoors.toArray(),
                                    "");

                //If a string was returned, say so.
                if ((lDoor != null)) {
                    Simulator.getInstance().OperateDoor(lDoor.GetVertical(), 
                            lDoor.GetBase(),
                            lDoor.GetFrom(), 
                            lDoor.GetTo(),
                            lbOpen);
                }
            }
             else if(e.getActionCommand() == "AddCleanSweep" || e.getActionCommand() == "RemoveCleanSweep" )
            {
                List<Tile> lChargeStations = Simulator.getInstance().GetAllChargeStations();
                boolean lbOpen = e.getActionCommand() == "OpenDoor" ? true : false;
                Tile lChargeStaion = (Tile)JOptionPane.showInputDialog(
                                    mFrame,
                                    "Please choose a chargestation for the cleansweep:\n",
                                    e.getActionCommand() == "OpenDoor" ?"Choose a chargestation to add a CleanSweep":"Choose a door to close",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    lChargeStations.toArray(),
                                    "");

                //If a string was returned, say so.
                if ((lChargeStaion != null)) {
                   int nID = CleanSweepManager.getInstance().CreateCleanSweep(lChargeStaion.GetX(), lChargeStaion.GetY());
                   mFloorplanPanel.AddCleanSweep(CleanSweepManager.getInstance().GetCleanSweep(nID));
                   //
                   CleanSweepManager.getInstance().StartCleanCycle(nID);
                }
            }
        }
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            MainFrame ex = new MainFrame();
            ex.setVisible(true);
        });
    }
}
