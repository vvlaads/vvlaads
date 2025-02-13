package lab.database;

import jakarta.persistence.*;

@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "X", nullable = false)
    private double x;
    @Column(name = "Y", nullable = false)
    private double y;
    @Column(name = "R", nullable = false)
    private double r;
    @Column(name = "result", nullable = false)
    private Boolean result;
    @Column(name = "createdTime", nullable = false)
    private String createdTime;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Point() {
    }

    public Point(double x, double y, double r, boolean result, String createdTime, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.createdTime = createdTime;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
