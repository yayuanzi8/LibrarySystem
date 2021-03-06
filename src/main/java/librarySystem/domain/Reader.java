package librarySystem.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class Reader implements UserDetails {
    private Integer credNum;
    private String name;
    private String password;
    private Date startTime;
    private Date endTime;
    private Integer maxAvailable;
    private String readerType;
    private Integer cumAvailNum;
    private String email;
    private Integer currentBorrowNum;
    private Set<? extends GrantedAuthority> authorities;

    public Integer getCurrentBorrowNum() {
        return currentBorrowNum;
    }

    public void setCurrentBorrowNum(Integer currentBorrowNum) {
        this.currentBorrowNum = currentBorrowNum;
    }

    public Integer getCredNum() {
        return credNum;
    }

    public void setCredNum(Integer credNum) {
        this.credNum = credNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return credNum + "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getMaxAvailable() {
        return maxAvailable;
    }

    public void setMaxAvailable(Integer maxAvailable) {
        this.maxAvailable = maxAvailable;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public Integer getCumAvailNum() {
        return cumAvailNum;
    }

    public void setCumAvailNum(Integer cumAvailNum) {
        this.cumAvailNum = cumAvailNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (credNum != null ? !credNum.equals(reader.credNum) : reader.credNum != null) return false;
        if (name != null ? !name.equals(reader.name) : reader.name != null) return false;
        return password != null ? password.equals(reader.password) : reader.password == null;
    }

    @Override
    public int hashCode() {
        int result = credNum != null ? credNum.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
