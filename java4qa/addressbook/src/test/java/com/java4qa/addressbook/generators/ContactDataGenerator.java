package com.java4qa.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java4qa.addressbook.model.ContactData;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count value.")
  public int count;

  @Parameter(names = "-f", description = "File path - absolute.")
  public String file;

  @Parameter(names = "-d", description = "Data format.")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generatorContacts(count);
    if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private List<ContactData> generatorContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 1; i < count + 1; i++) {
      contacts.add(new ContactData().withFirst(format("testFirst_%S", i))
            .withLast(format("tesLast_%S", i))
            .withHomePhone(format("testHome_%S", i))
            .withMobilePhone(format("testMob_%S", i))
            .withWorkPhone(format("testWork_%S", i))
            .withEmail(format("test_%S@address.com", i))
            .withEmail2(format("test_%S@address.com", i))
            .withEmail3(format("test_%S@address.com", i))
            .withPhoto(new File(format("src/test/resources/test_%S.jpg", i)))
      );
    }
    return contacts;
  }
}
