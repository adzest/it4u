package com.java4qa.addressbook.model;

import com.google.common.collect.ForwardingSet;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.*;


@SuppressWarnings("Convert2Diamond")
@Transactional
public class Groups extends ForwardingSet<GroupData>{

  private Set<GroupData> delegate;

  public Groups(Groups groups) {
    this.delegate = new HashSet<GroupData>(groups.delegate);
  }

  public Groups() {
    this.delegate = new HashSet<GroupData>();
  }

  public Groups(Collection<GroupData> groups){
    this.delegate = new HashSet<GroupData>(groups);
  }

  public Groups withAdded (GroupData group){
    Groups groups = new Groups(this);
    groups.add(group);
    return groups;
  }

  public Groups withOut (GroupData group){
    Groups groups = new Groups(this);
    groups.remove(group);
    return groups;
  }

  //TODO: DONE - Chapter 5 video 6: create wthModified group method to Groups class.
  public Groups withModified (GroupData modifiedGroup,GroupData group){
    Groups groups = new Groups(this);
    groups.remove(modifiedGroup);
    groups.add(group);
    return groups;
  }

  @Override
  protected Set<GroupData> delegate() {
    return delegate;
  }
}
