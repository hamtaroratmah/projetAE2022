package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;

public interface MemberDao {

    public MemberDTO getMember(String username);
}
