package com.lb.session.ext;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface Serializer {
  void setClassLoader(ClassLoader loader);

  byte[] serializeFrom(Object obj) throws IOException;

  Object deserializeInto(byte[] data) throws IOException, ClassNotFoundException;
}
