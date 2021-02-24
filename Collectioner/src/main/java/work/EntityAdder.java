package work;

import database.*;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;

public class EntityAdder
{
    public void addCollection(String entityCollectionName,String entityPageCount,String entityReleaseDate) throws errorMail
    {
        if (entityCollectionName.isEmpty() || entityPageCount.isEmpty() || entityReleaseDate.isEmpty())
        {
            throw new errorMail("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        CollectionEntity de = new CollectionEntity();
        de.setCollectionName(entityCollectionName);
        de.setPageCount(entityPageCount);
        de.setReleaseDate(Date.valueOf(entityReleaseDate));
        em.persist(de);
        em.getTransaction().commit();
    }

    public void addStamp(String entityStampName,String entityStampSeries,String entityStampCountry,String entityStampSize,String entityStampRelease,String entityIdCollection) throws errorMail
    {
        if (entityStampName.isEmpty() || entityStampSeries.isEmpty() || entityStampCountry.isEmpty() || entityStampSize.isEmpty() || entityStampRelease.isEmpty() || entityIdCollection.isEmpty())
        {
            throw new errorMail("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<StampEntity> stamp_list = em.createQuery("SELECT c FROM StampEntity c WHERE c.stampName='" + entityStampName + "'").getResultList();
        em.getTransaction().commit();

        if (!stamp_list.isEmpty())
        {
            throw new errorMail("Сlient with this number already exists");
        }

        em.getTransaction().begin();
        List <CollectionEntity> collection_list = em.createQuery("SELECT d FROM CollectionEntity d WHERE d.idCollection='" + Integer.parseInt(entityIdCollection) + "'").getResultList();
        em.getTransaction().commit();

        if (!collection_list.isEmpty())
        {
            em.getTransaction().begin();
            StampEntity ce = new StampEntity();
            ce.setStampName(entityStampName);
            ce.setStampSeries(entityStampSeries);
            ce.setStampCountry(entityStampCountry);
            ce.setStampSize(entityStampSize);
            ce.setStampRelease(Date.valueOf(entityStampRelease));
            ce.setCollectionByIdCollection(collection_list.get(0));
            em.persist(ce);
            em.getTransaction().commit();
        }
        else
        {
            throw new errorMail("there are no such collections");
        }
    }

    public void addPlace(String entityPlaceName,String entityPlaceDate,String entityCount,String entityPlaceSpot,String entityIdStamp) throws errorMail
    {
        if (entityPlaceName.isEmpty() || entityPlaceDate.isEmpty() || entityCount.isEmpty() || entityIdStamp.isEmpty())
        {
            throw new errorMail("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <StampEntity> stamp_list = em.createQuery("SELECT c FROM StampEntity c WHERE c.idPlace='" + Integer.parseInt(entityIdStamp) + "'").getResultList();
        em.getTransaction().commit();

        if (!stamp_list.isEmpty())
        {
            em.getTransaction().begin();
            PlaceEntity ve = new PlaceEntity();
            ve.setPlaceName(entityPlaceName);
            ve.setPlaceDate(Date.valueOf(entityPlaceDate));
            ve.setCount(Integer.parseInt(entityCount));
            ve.setPlaceSpot(entityPlaceSpot);
            ve.setStampByIdStamp(stamp_list.get(0));
            em.persist(ve);
            em.getTransaction().commit();
        }
        else
        { throw new errorMail("there are no such stamps");
        }
    }
}
    