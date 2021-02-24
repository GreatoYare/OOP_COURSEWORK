package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "stamp", schema = "collectioner")
public class StampEntity {
    private int idStamp;
    private String stampName;
    private String stampSeries;
    private String stampCountry;
    private String stampSize;
    private Date stampRelease;
    private CollectionEntity collectionByIdCollection;

    @Id
    @Column(name = "idStamp")
    public int getIdStamp() {
        return idStamp;
    }

    public void setIdStamp(int idStamp) {
        this.idStamp = idStamp;
    }

    @Basic
    @Column(name = "StampName")
    public String getStampName() {
        return stampName;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    @Basic
    @Column(name = "StampSeries")
    public String getStampSeries() {
        return stampSeries;
    }

    public void setStampSeries(String stampSeries) {
        this.stampSeries = stampSeries;
    }

    @Basic
    @Column(name = "StampCountry")
    public String getStampCountry() {
        return stampCountry;
    }

    public void setStampCountry(String stampCountry) {
        this.stampCountry = stampCountry;
    }

    @Basic
    @Column(name = "StampSize")
    public String getStampSize() {
        return stampSize;
    }

    public void setStampSize(String stampSize) {
        this.stampSize = stampSize;
    }

    @Basic
    @Column(name = "StampRelease")
    public Date getStampRelease() {
        return stampRelease;
    }

    public void setStampRelease(Date stampRelease) {
        this.stampRelease = stampRelease;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StampEntity stampEntity = (StampEntity) o;

        if (idStamp != stampEntity.idStamp) return false;
        if (stampName != null ? !stampName.equals(stampEntity.stampName) : stampEntity.stampName != null)
            return false;
        if (stampSeries != null ? !stampSeries.equals(stampEntity.stampSeries) : stampEntity.stampSeries != null)
            return false;
        if (stampCountry != null ? !stampCountry.equals(stampEntity.stampCountry) : stampEntity.stampCountry != null)
            return false;
        if (stampSize != null ? !stampSize.equals(stampEntity.stampSize) : stampEntity.stampSize != null)
            return false;
        if (stampRelease != null ? !stampRelease.equals(stampEntity.stampRelease) : stampEntity.stampRelease != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idStamp;
        result = 31 * result + (stampName != null ? stampName.hashCode() : 0);
        result = 31 * result + (stampSeries != null ? stampSeries.hashCode() : 0);
        result = 31 * result + (stampCountry != null ? stampCountry.hashCode() : 0);
        result = 31 * result + (stampSize != null ? stampSize.hashCode() : 0);
        result = 31 * result + (stampRelease != null ? stampRelease.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idCollection", referencedColumnName = "idCollection", nullable = false)
    public CollectionEntity getCollectionByIdCollection() {
        return collectionByIdCollection;
    }

    public void setCollectionByIdCollection(CollectionEntity collectionByIdCollection) {
        this.collectionByIdCollection = collectionByIdCollection;
    }
}

   