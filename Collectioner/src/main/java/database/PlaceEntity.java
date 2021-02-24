package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "place", schema = "collectioner")
public class PlaceEntity {
    private int idPlace;
    private String placeName;
    private Date placeDate;
    private int placePage;
    private String placeSpot;
    private StampEntity stampByIdStamp;

    @Id
    @Column(name = "idPlace")
    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    @Basic
    @Column(name = "PlaceName")
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Basic
    @Column(name = "PlaceDate")
    public PlaceDate getPlaceDate() {
        return placePlaceDate;
    }

    public void setPlaceDate(PlaceDate placePlaceDate) {
        this.placePlaceDate = placePlaceDate;
    }

    @Basic
    @Column(name = "Count")
    public int getCount() {
        return placePage;
    }

    public void setCount(int placePage) {
        this.placePage = placePage;
    }

    @Basic
    @Column(name = "PlaceSpot")
    public String getPlaceSpot() {
        return placeSpot;
    }

    public void setPlaceSpot(String placeSpot) {
        this.placeSpot = placeSpot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceEntity that = (PlaceEntity) o;

        if (idPlace != that.idPlace) return false;
        if (placePage != that.placePage) return false;
        if (placeName != null ? !placeName.equals(that.placeName) : that.placeName != null)
            return false;
        if (placeDate != null ? !placeDate.equals(that.placeDate) : that.placeDate != null) return false;
        if (placeSpot != null ? !placeSpot.equals(that.placeSpot) : that.placeSpot != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPlace;
        result = 31 * result + (placeName != null ? placeName.hashCode() : 0);
        result = 31 * result + (placeDate != null ? placeDate.hashCode() : 0);
        result = 31 * result + placePage;
        result = 31 * result + (placeSpot != null ? placeSpot.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "idPlace", nullable = false)
    public StampEntity getStampByIdStamp() {
        return stampByIdStamp;
    }

    public void setStampByIdStamp(StampEntity stampByIdStamp) {
        this.stampByIdStamp = stampByIdStamp;
    }
}
