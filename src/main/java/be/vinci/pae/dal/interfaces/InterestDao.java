package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface InterestDao {
  InterestDTO getInterest(int idInterest);

  ArrayList<InterestDTO> listInterests(int idItem);

  InterestDTO createInterestInstance(ResultSet resultSet);
}
