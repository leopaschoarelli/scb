package br.com.gori.scb.entidade;

import br.com.gori.scb.entidade.util.RoleUser;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "roles")
@NamedQuery(name = "Role.findRoleByType", query = "select r from Role r where r.typeRole = :type")
@SequenceGenerator(name = "ROLE_SEQUENCE", sequenceName = "ROLE_SEQUENCE", allocationSize = 1, initialValue = 0)
public class Role extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQUENCE")
    private Long id;
    @Column(length = 255, nullable = false, unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private RoleUser typeRole;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users = new HashSet<>(0);

    public Role() {
    }

    public Role(String name, RoleUser typeRole) {
        this.name = name;
        this.typeRole = typeRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public RoleUser getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(RoleUser typeRole) {
        this.typeRole = typeRole;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
