package work;

import database.*;

import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TableModelUpdater
{
    public static String[] columns;
    public static DefaultTableModel model;

    public void updateCollection()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<CollectionEntity> collection_list = em.createQuery("SELECT d FROM CollectionEntity d ORDER BY d.collectionName, d.pageCount").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idCollection","Last name","First name","Birthday date"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<collection_list.size();i++)
        {
            CollectionEntity de = collection_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(de.getIdCollection()),de.getCollectionName(),de.getPageCount(),date.format(de.getReleaseDate())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updateStamp()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<StampEntity> stamp_list = em.createQuery("SELECT c FROM StampEntity c ORDER BY c.idStamp").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idStamp","Stamp number","StampSeries","StampCountry","StampSize","Delivery date","idCollection"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<stamp_list.size();i++)
        {
            StampEntity ce = stamp_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(ce.getIdStamp()),ce.getStampName(),ce.getStampSeries(),ce.getStampCountry(),ce.getStampSize(),date.format(ce.getStampRelease()),Integer.toString(ce.getCollectionByIdCollection().getIdCollection())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updatePlace()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<PlaceEntity> place_list = em.createQuery("SELECT v FROM PlaceEntity v ORDER BY v.placeName").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idPlace","Place name","Date","Count","Release number","idStamp"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<place_list.size();i++)
        {
            PlaceEntity ve = place_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(ve.getIdPlace()),ve.getPlaceName(),date.format(ve.getDate()),Integer.toString(ve.getCount()),ve.getPlaceSpot(),Integer.toString(ve.getStampByIdStamp().getIdStamp())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updateFindPlaces(String entityIdCollection) throws errorMail
    {
        if (entityIdCollection.isEmpty())
        {
            throw new errorMail("Enter idCollection");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <CollectionEntity> collection_list = em.createQuery("SELECT d FROM CollectionEntity d WHERE d.idCollection='" + Integer.parseInt(entityIdCollection) + "'").getResultList();
        em.getTransaction().commit();

        if (!collection_list.isEmpty())
        {
            columns = new String[] {"idCollection","Last name","First name","Stamp number","Last name 2","First name 2","Delivery date","Place name"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            CollectionEntity de = collection_list.get(0);

            em.getTransaction().begin();
            List <StampEntity> stamp_list = em.createQuery("SELECT c FROM StampEntity c WHERE c.collectionByIdCollection.idCollection ='" + de.getIdCollection() + "'").getResultList();
            em.getTransaction().commit();

            if (!stamp_list.isEmpty())
            {
                for (int i=0; i<stamp_list.size(); i++)
                {
                    em.getTransaction().begin();
                    List <PlaceEntity> place_list = em.createQuery("SELECT v FROM PlaceEntity v WHERE v.stampByIdStamp.idStamp = '" + stamp_list.get(i).getIdStamp() + "'").getResultList();
                    em.getTransaction().commit();

                    if (!place_list.isEmpty())
                    {
                        for (int j=0; j<place_list.size(); j++)
                        {
                            String[] Row = new String[8];
                            Row[0]=Integer.toString(de.getIdCollection());
                            Row[1]=de.getCollectionName();
                            Row[2]=de.getPageCount();
                            Row[3]=stamp_list.get(i).getStampName();
                            Row[4]=stamp_list.get(i).getStampSeries();
                            Row[5]=stamp_list.get(i).getStampCountry();
                            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                            Row[6]=date.format(stamp_list.get(j).getStampRelease());
                            Row[7]=place_list.get(j).getPlaceName();
                            model.addRow(Row);
                        }
                    }
                    else {
                        for (int j = 0; j < stamp_list.size(); j++) {
                            String[] Row = new String[8];
                            Row[0] = Integer.toString(de.getIdCollection());
                            Row[1] = de.getCollectionName();
                            Row[2] = de.getPageCount();
                            Row[3] = stamp_list.get(i).getStampName();
                            Row[4] = stamp_list.get(i).getStampSeries();
                            Row[5] = stamp_list.get(i).getStampCountry();
                            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                            Row[6] = date.format(stamp_list.get(j).getStampRelease());
                            Row[7] = "";
                            model.addRow(Row);
                        }
                    }
                }
            }
            else
            {
                String[] row = new String[8];
                row[0]=Integer.toString(de.getIdCollection());
                row[1]=de.getCollectionName();
                row[2]= de.getPageCount();
                row[3]="";
                row[4]="";
                row[5]="";
                row[6]="";
                row[7]="";
                model.addRow(row);
            }
            form_GUI.table_model = model;
        }
        else
        {
            throw new errorMail("there are no such collections");
        }
    }

    public void updateFindCollection(String idStamp) throws errorMail
    {
        if (idStamp.isEmpty())
        {
            throw new errorMail("Enter idStamp");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <StampEntity> stamp_list = em.createQuery("SELECT c FROM StampEntity c WHERE c.idStamp ='" + Integer.parseInt(idStamp) + "'").getResultList();
        em.getTransaction().commit();

        if (!stamp_list.isEmpty())
        {
            columns = new String[]{"Last name", "First name", "Stamp number","StampSeries","StampCountry","StampSize","Delivery date"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {
                    stamp_list.get(0).getCollectionByIdCollection().getCollectionName(),
                    stamp_list.get(0).getCollectionByIdCollection().getPageCount(),
                    stamp_list.get(0).getStampName(),
                    stamp_list.get(0).getStampSeries(),
                    stamp_list.get(0).getStampCountry(),
                    stamp_list.get(0).getStampSize(),
                    date.format(stamp_list.get(0).getStampRelease())
            };
            model.addRow(row);
            form_GUI.table_model = model;
        }
        else
        {
            throw new errorMail("there are no such stamps");
        }
    }

    public void updateFindStamp(String idPlace) throws errorMail
    {
        if (idPlace.isEmpty())
        {
            throw new errorMail("Enter Place name");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <PlaceEntity> place_list = em.createQuery("SELECT c FROM PlaceEntity c WHERE c.placeName ='" + idPlace + "'").getResultList();
        em.getTransaction().commit();

        if (!place_list.isEmpty())
        {


                columns = new String[]{"Id Stamp", "Last name 2", "First name 2", "Place name", "Date", "Count", "Release number"};
                model = new DefaultTableModel(columns, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
            for (int j=0; j<place_list.size(); j++) {
                String[] Row = new String[7];
                Row[0] = String.valueOf(place_list.get(j).getStampByIdStamp().getIdStamp());
                Row[1] = String.valueOf(place_list.get(j).getStampByIdStamp().getStampSeries());
                Row[2] = String.valueOf(place_list.get(j).getStampByIdStamp().getStampCountry());
                Row[3] = place_list.get(j).getPlaceName();
                DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                Row[4] = date.format(place_list.get(j).getDate());
                Row[5] = String.valueOf(place_list.get(j).getCount());
                Row[6] = place_list.get(j).getPlaceSpot();
                model.addRow(Row);
            }
            form_GUI.table_model = model;
        }
        else
        {
            throw new errorMail("there are no such places");
        }
    }

    public void updateReport(String startDate, String endDate) throws errorMail
    {
        if (startDate.isEmpty() || endDate.isEmpty())
        {
            throw new errorMail("Enter Start/End dates");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <PlaceEntity> place_list = em.createQuery("SELECT v FROM PlaceEntity v WHERE v.date >= '" + Date.valueOf(startDate) + "' AND v.date <= '" + Date.valueOf(endDate) + "'").getResultList();
        em.getTransaction().commit();

        if (!place_list.isEmpty())
        {
            columns = new String[] {"Last name","First name","Stamp number","Place name","Date","Count","Release number"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            for (int i=0; i<place_list.size(); i++) {
                DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                String[] row = {
                        place_list.get(i).getStampByIdStamp().getCollectionByIdCollection().getCollectionName(),
                        place_list.get(i).getStampByIdStamp().getCollectionByIdCollection().getPageCount(),
                        place_list.get(i).getStampByIdStamp().getStampName(),
                        place_list.get(i).getPlaceName(),
                        date.format(place_list.get(i).getDate()),
                        Integer.toString(place_list.get(i).getCount()),
                        place_list.get(i).getPlaceSpot()};
                model.addRow(row);
            }
            form_GUI.table_model=model;
        }
        else
        {
            throw new errorMail("no places were found");
        }
    }
}