package com.id3.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_personnel_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonnelInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personnel_id")
    private Integer personnelId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "manager_id")
    private Integer managerId;

    @OneToMany(mappedBy = "managerId", fetch = FetchType.LAZY)
    private List<PersonnelInfo> subordinates;

    @Column(name = "started_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp startedAt;

    @Column(name = "department_name", nullable = false, length = 50)
    private String departmentName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "position", nullable = false, length = 50)
    private String position;

    @Override
    public String toString() {
        return "PersonnelInfo{" +
                "personnelId=" + personnelId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role=" + role +
                ", subordinates=" + (subordinates != null ? subordinates.stream()
                .map(personnel -> "PersonnelInfo{" +
                        "personnelId=" + personnel.getPersonnelId() +
                        ", firstName='" + personnel.getFirstName() + '\'' +
                        ", lastName='" + personnel.getLastName() + '\'' +
                        ", email='" + personnel.getEmail() + '\'' +
                        ", role=" + personnel.getRole() +
                        ", departmentName='" + personnel.getDepartmentName() + '\'' +
                        ", status=" + personnel.getStatus() +
                        '}')
                .collect(Collectors.joining(", ")) : "[]")+
               // ", manager=" + (manager != null ? manager.getPersonnelId() : null) +
                ", startedAt=" + startedAt +
                ", departmentName='" + departmentName + '\'' +
                ", status=" + status +
                ", position= " +position +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getPassword() {
        return this.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}
