package librarySystem.security;

import librarySystem.dao.PermissionDao;
import librarySystem.dao.ReaderDao;
import librarySystem.dao.ReaderPermissionDao;
import librarySystem.domain.Permission;
import librarySystem.domain.Reader;
import librarySystem.domain.ReaderPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReaderDetailsService implements UserDetailsService {

    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private ReaderPermissionDao readerPermissionDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String cred_num) throws UsernameNotFoundException {
        Reader reader = readerDao.findByCredNum(Integer.parseInt(cred_num));
        if (reader == null)
            throw new UsernameNotFoundException("用户名或密码错误！");
        Set<? extends GrantedAuthority> authorities = getAuthorities(Integer.parseInt(cred_num));
        reader.setAuthorities(authorities);
        return reader;
    }

    private Set<? extends GrantedAuthority> getAuthorities(Integer credNum) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<ReaderPermission> readerPermissionList = readerPermissionDao.findByCredNum(credNum);
        for (ReaderPermission readerPermission : readerPermissionList) {
            Permission permission = permissionDao.find(readerPermission.getPid());
            authorities.add(new SimpleGrantedAuthority(permission.getPname()));
        }
        return authorities;
    }
}
