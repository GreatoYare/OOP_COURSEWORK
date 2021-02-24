package work;

import database.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import database.CollectionEntity;

import javax.persistence.EntityManager;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class form_GUI extends JDialog
{
    private JPanel ExternalPanel;
    private JTabbedPane TabbedPane;
    private JPanel collectionPanel;
    private JButton AddUpdateButton;
    private JTextField collectionLastnameTextField;
    private JTextField collectionFirstnameTextField;
    private JTextField collectionBirthdaydateTextField;
    private JButton deleteButton;
    private JPanel stampPanel;
    private JButton renewButton;
    private JTextField collectionStampsTextField;
    private JButton collectionOkButton;
    //private JTextField stampFinddirverTextField;
    private JTextField placeFinddirverTextField;
    private JButton placeOkButton;
    private JPanel placePanel;
    //private JButton exportReportButton;
    private JPanel FreeTablePanel;
    private JTable table;
    private JTextField stampNameTextField;
    private JTextField stampStampSeriesTextField;
    private JTextField stampStampCountryTextField;
    private JTextField stampStampSizeTextField;
    private JTextField stampDeliverydateTextField;
    private JTextField stampidCollectionTextField;
    private JTextField placeNameTextField;
    private JTextField placeDateTextField;
    private JTextField placeCountTextField;
    private JTextField placePlaceSpotTextField;
    private JTextField placeidStampTextField;
    private JTextField placeStartdateTextField;
    private JTextField placeEnddateTextField;
    private JPanel TablePanel;
    private JScrollPane TableScrollPane;
    private JTextField countTextField;
   // private JButton reportOkButton;

    public static DefaultTableModel table_model;

    public static final SessionFactory ourSessionFactory;
    static
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        ourSessionFactory = configuration.buildSessionFactory();
    }
    public static Session getSession() throws HibernateException
    {
        return ourSessionFactory.openSession();
    }

    TableModelUpdater tmUpdater = new TableModelUpdater();
    EntityAdder entityAdder = new EntityAdder();
    EntityUpdater entityUpdater = new EntityUpdater();

    public static int count_of_collections()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select d from CollectionEntity d").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_stamps()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select c.idStamp from StampEntity c").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_places()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select v.idPlace from PlaceEntity v").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public void clear_GUI()
    {
        AddUpdateButton.setText("Add new record");
        collectionLastnameTextField.setText("");
        collectionFirstnameTextField.setText("");
        collectionBirthdaydateTextField.setText("");
        collectionStampsTextField.setText("");

        placeFinddirverTextField.setText("");
        //stampFinddirverTextField.setText("");
        stampNameTextField.setText("");
        stampStampSeriesTextField.setText("");
        stampStampCountryTextField.setText("");
        stampStampSizeTextField.setText("");
        stampDeliverydateTextField.setText("");
        stampidCollectionTextField.setText("");

        placeNameTextField.setText("");
        placeDateTextField.setText("");
        placeCountTextField.setText("");
        placePlaceSpotTextField.setText("");
        placeidStampTextField.setText("");
        //placeStartdateTextField.setText("");
        //placeEnddateTextField.setText("");

        int tabIndex = TabbedPane.getSelectedIndex();
        switch (tabIndex)
        {
            case 0:
                tmUpdater.updateCollection();
                countTextField.setText(Integer.toString(count_of_collections()));
                break;
            case 1:
                tmUpdater.updateStamp();
                countTextField.setText(Integer.toString(count_of_stamps()));
                break;
            case 2:
                tmUpdater.updatePlace();
                countTextField.setText(Integer.toString(count_of_places()));
                break;
        }
        table.setModel(table_model);
    }

    public form_GUI()
    {
        clear_GUI();
        setContentPane(ExternalPanel);
        //setModal(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        });

        TabbedPane.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                clear_GUI();
            }
        });

        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                AddUpdateButton.setText("Update record");
                int tabIndex = TabbedPane.getSelectedIndex();

                switch (tabIndex) {
                    case 0:
                        String collectionCollectionName = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String collectionPageCount = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String collectionReleaseDate = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        collectionLastnameTextField.setText(collectionCollectionName);
                        collectionFirstnameTextField.setText(collectionPageCount);
                        collectionBirthdaydateTextField.setText(collectionReleaseDate);
                        break;
                    case 1:
                        String stampName = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String stampStampSeries = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String stampStampCountry = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String stampStampSize = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        String stampStampRelease = table_model.getValueAt(table.getSelectedRow(), 5).toString();
                        String stampIdCollection = table_model.getValueAt(table.getSelectedRow(), 6).toString();
                        stampNameTextField.setText(stampName);
                        stampStampSeriesTextField.setText(stampStampSeries);
                        stampStampCountryTextField.setText(stampStampCountry);
                        stampStampSizeTextField.setText(stampStampSize);
                        stampDeliverydateTextField.setText(stampStampRelease);
                        stampidCollectionTextField.setText(stampIdCollection);
                        break;
                    case 2:
                        String placeName = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String placeDate = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String placeCount = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String placePlaceSpot = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        String placeIdStamp = table_model.getValueAt(table.getSelectedRow(), 5).toString();
                        placeNameTextField.setText(placeName);
                        placeDateTextField.setText(placeDate);
                        placeCountTextField.setText(placeCount);
                        placeidStampTextField.setText(placeIdStamp);
                        placePlaceSpotTextField.setText(placePlaceSpot);
                        break;
                }
            }
        });

        AddUpdateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int tabIndex = TabbedPane.getSelectedIndex();
                int rowIndex = table.getSelectedRow();

                if (rowIndex==-1)
                {
                    switch (tabIndex)
                    {
                        case 0:
                            String collectionCollectionName = collectionLastnameTextField.getText();
                            String collectionPageCount = collectionFirstnameTextField.getText();
                            String collectionReleaseDate = collectionBirthdaydateTextField.getText();

                            try
                            {
                                entityAdder.addCollection(collectionCollectionName, collectionPageCount, collectionReleaseDate);
                                tmUpdater.updateCollection();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 1:
                            String stampName = stampNameTextField.getText();
                            String stampStampSeries = stampStampSeriesTextField.getText();
                            String stampStampCountry = stampStampCountryTextField.getText();
                            String stampStampSize = stampStampSizeTextField.getText();
                            String stampStampRelease = stampDeliverydateTextField.getText();
                            String stampIdCollection = stampidCollectionTextField.getText();

                            try
                            {
                                entityAdder.addStamp(stampName, stampStampSeries, stampStampCountry, stampStampSize, stampStampRelease, stampIdCollection);
                                tmUpdater.updateStamp();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            String placeName = placeNameTextField.getText();
                            String placeDate = placeDateTextField.getText();
                            String placeCount = placeCountTextField.getText();
                            String placePlaceSpot = placePlaceSpotTextField.getText();
                            String placeIdStamp = placeidStampTextField.getText();

                            try
                            {
                                entityAdder.addPlace(placeName, placeDate, placeCount, placePlaceSpot, placeIdStamp);
                                tmUpdater.updatePlace();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }
                else
                {
                    EntityManager em = ourSessionFactory.createEntityManager();
                    switch (tabIndex)
                    {
                        case 0:
                            em.getTransaction().begin();
                            CollectionEntity de = em.find(CollectionEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String collectionCollectionName = collectionLastnameTextField.getText();
                            String collectionPageCount = collectionFirstnameTextField.getText();
                            String collectionReleaseDate = collectionBirthdaydateTextField.getText();

                            try
                            {
                                entityUpdater.updateCollection(de.getIdCollection(),collectionCollectionName, collectionPageCount, collectionReleaseDate);
                                tmUpdater.updateCollection();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 1:
                            em.getTransaction().begin();
                            StampEntity ce = em.find(StampEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String stampName = stampNameTextField.getText();
                            String stampStampSeries = stampStampSeriesTextField.getText();
                            String stampStampCountry = stampStampCountryTextField.getText();
                            String stampStampSize = stampStampSizeTextField.getText();
                            String stampStampRelease = stampDeliverydateTextField.getText();
                            String stampIdCollection = stampidCollectionTextField.getText();

                            try
                            {
                                entityUpdater.updateStamp(ce.getIdStamp(),stampName, stampStampSeries, stampStampCountry, stampStampSize, stampStampRelease, stampIdCollection);
                                tmUpdater.updateStamp();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            em.getTransaction().begin();
                            PlaceEntity ve = em.find(PlaceEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String placeName = placeNameTextField.getText();
                            String placeDate = placeDateTextField.getText();
                            String placeCount = placeCountTextField.getText();
                            String placePlaceSpot = placePlaceSpotTextField.getText();
                            String placeIdStamp = placeidStampTextField.getText();

                            try
                            {
                                entityUpdater.updatePlace(ve.getIdPlace(),placeName, placeDate, placeCount, placePlaceSpot, placeIdStamp);
                                tmUpdater.updatePlace();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }

                table.setModel(table_model);
                clear_GUI();
            }
        });

        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int tabIndex = TabbedPane.getSelectedIndex();
                int rowIndex = table.getSelectedRow();
                EntityManager em = ourSessionFactory.createEntityManager();
                em.getTransaction().begin();

                switch (tabIndex)
                {
                    case 0:
                        CollectionEntity de = em.find(CollectionEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(de);
                        em.getTransaction().commit();
                        tmUpdater.updateCollection();
                        break;
                    case 1:
                        StampEntity ce = em.find(StampEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(ce);
                        em.getTransaction().commit();
                        tmUpdater.updateStamp();
                        break;
                    case 2:
                        PlaceEntity ve = em.find(PlaceEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(ve);
                        em.getTransaction().commit();
                        tmUpdater.updatePlace();
                        break;
                }
                table.setModel(table_model);
                clear_GUI();
            }
        });

        renewButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clear_GUI();
            }
        });

        collectionOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String collectionIdStamp = collectionStampsTextField.getText();
                try
                {
                    tmUpdater.updateFindPlaces(collectionIdStamp);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

     /*   stampOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String idStamp = stampFinddirverTextField.getText();
                try
                {
                    tmUpdater.updateFindCollection(idStamp);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });*/

        placeOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String idPlace = placeFinddirverTextField.getText();
                try
                {
                    tmUpdater.updateFindStamp(idPlace);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

       /* reportOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String startDate = placeStartdateTextField.getText();
                String endDate = placeEnddateTextField.getText();
                try
                {
                    tmUpdater.updateReport(startDate,endDate);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });*/

       /* exportReportButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String startDate = placeStartdateTextField.getText();
                String endDate = placeEnddateTextField.getText();
                try
                {
                    tmUpdater.updateReport(startDate,endDate);
                    table.setModel(table_model);
                    //ExportPdf.createPdf("Reports/period1.pdf", new String[]{"Last name","First name","Stamp number","Place name","Date","Count","Other punishment"}, table_model);
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, "Cant export: " + exception.getClass() + ':' + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });*/
    }

    private void exit()
    {
        dispose();
    }

    public static void main(String[] args)
    {
        form_GUI dialog = new form_GUI();
        dialog.setTitle("MAIL");
        dialog.setSize(1000,380);
        dialog.setLocation(300,150);
        dialog.setVisible(true);
    }
}