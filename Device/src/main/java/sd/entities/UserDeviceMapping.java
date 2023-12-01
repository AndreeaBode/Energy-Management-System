package sd.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserDeviceMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private int deviceId;

    public UserDeviceMapping() {
    }

    public UserDeviceMapping(int userId, int deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
    @Override
    public String toString() {
        return "UserDeviceMapping{" +
                "id=" + id +
                ", userId=" + userId +
                ", deviceId=" + deviceId +
                '}';
    }

}

