package me.rbw.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import me.rbw.web.RbwAuth;

@Entity
public class Bike {

    @Id
    private String id;
    private String name;
    @Parent()
    private Key<User> owner;
    private String vin;

    private Bike() {
    }

    public Bike(String ownerId) {
        owner = Key.create(User.class, ownerId);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVin() {
        return vin;
    }

    public static BikeBuilder create(String ownerId) {
        return new BikeBuilder(ownerId);
    }

    public static class BikeBuilder {
        private final Bike target;

        public BikeBuilder(String ownerId) {
            target = new Bike(ownerId);
            target.id = RbwAuth.generateUUID();
        }

        public BikeBuilder withFriendlyName(String friendlyName) {
            target.name = friendlyName;
            return this;
        }

        public BikeBuilder withVIN(String vin) {
            target.vin = vin;
            return this;
        }

        public Bike build() {
            return target;
        }
    }
}
