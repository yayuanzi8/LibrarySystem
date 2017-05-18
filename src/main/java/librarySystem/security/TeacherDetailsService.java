package librarySystem.security;

import librarySystem.dao.TeacherDao;
import librarySystem.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by yayuanzi8 on 2017/5/18 0018.
 */
public class TeacherDetailsService implements UserDetailsService {

    private TeacherDao teacherDao;

    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Override
    public UserDetails loadUserByUsername(String tid) throws UsernameNotFoundException {
        Teacher teacher = teacherDao.find(tid);
        if (teacher!=null)
            return teacher;
        throw new UsernameNotFoundException("用户名或密码错误！");
    }
}
