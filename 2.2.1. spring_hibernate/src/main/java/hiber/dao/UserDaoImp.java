package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User identifyUserByHisCar(String model, int series) {
        String hql = "FROM User AS u INNER JOIN FETCH u.car "
                + "WHERE u.car.model = :paramModel AND u.car.series = :paramSeries";
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery(hql, User.class)
                .setParameter("paramModel", model)
                .setParameter("paramSeries", series);
        List<User> list = query.getResultList();
        System.out.println(list.get(0).toString());
        return list.get(0);
    }

}
