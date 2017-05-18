package librarySystem.security;

import librarySystem.dao.PermissionDao;
import librarySystem.dao.StudentAndPermissionDao;
import librarySystem.dao.StudentDao;
import librarySystem.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StudentDetailsService implements UserDetailsService {

    private StudentDao studentDao;
    private StudentAndPermissionDao studentAndPermissionDao;
    private PermissionDao permissionDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Autowired
    public void setStudentAndPermissionDao(StudentAndPermissionDao studentAndPermissionDao) {
        this.studentAndPermissionDao = studentAndPermissionDao;
    }

    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Override
    public UserDetails loadUserByUsername(String no) throws UsernameNotFoundException {
        Student student = studentDao.find(no);
        if (student != null) {
            List<GrantedAuthority> grantedAuthorities = buildUserAuthority(no);
            student.setAuthorities(grantedAuthorities);
            return student;
        }
        throw new UsernameNotFoundException("用户名或密码错误！");
    }

    private List<GrantedAuthority> buildUserAuthority(String no) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        studentAndPermissionDao.findBySno(no).forEach(student_permission ->
                grantedAuthorities.add(new SimpleGrantedAuthority(permissionDao.find(student_permission.getPid()).getPname())));
        return grantedAuthorities;
    }
}
