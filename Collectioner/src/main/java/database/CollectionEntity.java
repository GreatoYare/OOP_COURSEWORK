package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "collection", schema = "collectioner")
public class CollectionEntity {
    private int idCollection;
    private String collectionName;
    private String pageCount;
    private Date releaseDate;

    @Id
    @Column(name = "idCollection")
    public int getIdCollection() {
        return idCollection;
    }

    public void setIdCollection(int idCollection) {
        this.idCollection = idCollection;
    }

    @Basic
    @Column(name = "CollectionName")
    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @Basic
    @Column(name = "PageCount")
    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    @Basic
    @Column(name = "ReleaseDate")
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectionEntity that = (CollectionEntity) o;

        if (idCollection != that.idCollection) return false;
        if (collectionName != null ? !collectionName.equals(that.collectionName) : that.collectionName != null) return false;
        if (pageCount != null ? !pageCount.equals(that.pageCount) : that.pageCount != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCollection;
        result = 31 * result + (collectionName != null ? collectionName.hashCode() : 0);
        result = 31 * result + (pageCount != null ? pageCount.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        return result;
    }
}