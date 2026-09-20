package vn.iotstar.model;

import java.io.Serializable;
import java.sql.Date;
import jakarta.persistence.*; // Thêm thư viện JPA

@Entity // Khai báo class này là một Entity quản lý bởi JPA
@Table(name = "[User]") // Ánh xạ chính xác với tên bảng trong SQL Server
@SuppressWarnings("serial")
public class User implements Serializable {
    
    @Id // Xác định đây là khóa chính (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
    private int id;
    
    private String email;
    private String userName;
    private String fullName;
    private String password;
    private String avatar; // Trường này sẽ dùng để lưu đường dẫn file ảnh (images) upload lên
    private int roleid;
    private String phone;
    private Date createdDate;

    // Hàm khởi tạo mặc định
    public User() {
        super();
    }

    // Hàm khởi tạo có tham số
    public User(String email, String userName, String fullName, String password, String avatar, int roleid,
            String phone, Date createdDate) {
        super();
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.avatar = avatar;
        this.roleid = roleid;
        this.phone = phone;
        this.createdDate = createdDate;
    }

    // Các hàm Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}