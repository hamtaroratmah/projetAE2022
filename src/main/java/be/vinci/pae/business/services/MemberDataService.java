package be.vinci.pae.business.services;

import be.vinci.pae.business.domain.interfaces.Member;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public interface MemberDataService {

  List<Member> getAll();

  Member getOne(int id);

  Member getOne(String login);

  Member createOne(Member item);

  int nextItemId();

  ObjectNode login(String login, String password);

  ObjectNode register(Member member);
}
