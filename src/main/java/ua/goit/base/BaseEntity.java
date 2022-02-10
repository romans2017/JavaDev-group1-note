package ua.goit.base;


import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

@FunctionalInterface
public interface BaseEntity<ID> extends Serializable {

    @SerializedName("id")
    ID getId();
}
