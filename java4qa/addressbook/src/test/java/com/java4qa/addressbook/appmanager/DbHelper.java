package com.java4qa.addressbook.appmanager;

import com.java4qa.addressbook.model.ContactData;
import com.java4qa.addressbook.model.Contacts;
import com.java4qa.addressbook.model.GroupData;
import com.java4qa.addressbook.model.Groups;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DbHelper {

  private final SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
          .configure() // configures settings from hibernate.cfg.xml
          .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Groups groups() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData").list();
    session.getTransaction().commit();
    session.close();
    return new Groups(result);
  }

  public Contacts contacts() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session.createQuery("from ContactData where deprecated ='0000-00-00'").list();
    session.getTransaction().commit();
    session.close();
    return new Contacts(result);
  }

  public Boolean isContactInGroup(Integer contactId, Integer groupId) {
    Connection conn = null;
    Boolean blnIsContactInGroup = false;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
      Statement st = conn.createStatement();
      String strQuery = String.format("select id from address_in_groups where (id=%s and group_id=%s)", contactId, groupId);
      System.out.println("query " + strQuery);
      ResultSet rs = st.executeQuery(strQuery);
      while (rs.next()) {
        String contactDbId = rs.getString("id");
        System.out.println(contactDbId.toString());
        if (contactDbId != null) {
          assertThat(contactDbId, equalTo(String.valueOf(contactId)));
          blnIsContactInGroup = true;
        }
      }
      rs.close();
      st.close();
      conn.close();
      System.out.println(rs);
    } catch (
          SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    return blnIsContactInGroup;
  }
}