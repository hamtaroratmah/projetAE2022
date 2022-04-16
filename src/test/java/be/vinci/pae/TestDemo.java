package be.vinci.pae;

//@TestInstance(Lifecycle.PER_CLASS)
//public class TestDemo {
//
//  private MemberUCC memberUCC;
//
//  private Member member;
//  private MemberDao memberDao;
//  private ItemUCC itemUCC;
//  private ItemDTO itemDTO;
//  private List<ItemDTO> items;
//  private OfferUCC offerUCC;
//  private OfferDTO offerDTO;
//
//  @BeforeAll
//  void initAll() {
////    Config.load("dev.properties");
//    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
//    DomainFactory domainFactory = locator.getService(DomainFactory.class);
//
//    memberUCC = locator.getService(MemberUCC.class);
//    memberDao = locator.getService(MemberDao.class);
//    member = (Member) domainFactory.getMember();
//    Member memberBiz = (Member) domainFactory.getMember();
//    member.setPassword(memberBiz.hashPassword("password"));
//    member.setUsername("username");
//    member.setIdMember(1);
//
//    Mockito.when(member.getIdMember()).thenReturn(1);
//    Mockito.when(member.getPassword()).thenReturn("password");
//    Mockito.when(member.getUsername()).thenReturn("username");
//    Mockito.when(memberBiz.checkPassword(member.getPassword())).thenReturn(true);
//    Mockito.when(memberDao.getMemberByUsername(member.getUsername())).thenReturn(member);
//    try {
//      Mockito.when(memberDao.getMember(member.getIdMember())).thenReturn(member);
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//
//    itemUCC = locator.getService(ItemUCC.class);
//    ItemDao itemDao = locator.getService(ItemDao.class);
//    itemDTO = locator.getService(ItemDTO.class);
//    Mockito.when(itemDTO.getIdItem()).thenReturn(1);
//    Mockito.when(itemDao.getItem(itemDTO.getIdItem())).thenReturn(itemDTO);
//    items = new ArrayList<>();
//    Mockito.when(itemDao.getGivenItems()).thenReturn(items);
//
//    offerUCC = locator.getService(OfferUCC.class);
//    OfferDao offerDao = locator.getService(OfferDao.class);
//    offerDTO = locator.getService(OfferDTO.class);
//    Mockito.when(offerDTO.getIdOffer()).thenReturn(1);
//    Mockito.when(offerDao.getOffer(offerDTO.getIdOffer())).thenReturn(offerDTO);
//  }
//
//  @DisplayName("Tests MemberUCC")
//  @Test
//  public void getOneLogin() {
//    assertAll(
//        () -> assertEquals(member, memberUCC.getOneByUsername(member.getUsername())),
//        () -> assertNull(memberUCC.getOneByUsername("badUsername"))
//    );
//  }
//
//  @Test
//  public void getOneId() {
//    assertAll(
//        () -> assertEquals(member, memberUCC.getOne(member.getIdMember())),
//        () -> assertNull(memberUCC.getOne(5))
//    );
//  }
//
//  //  @Test
//  //  public void login() {
//  //    assertAll(
//  //        () -> assertEquals(member, memberUCC.login(member.getUsername(), member.getPassword())),
//  //        () ->
//  //        Mockito.verify(memberDao, Mockito.times(2)).getMemberByUsername(member.getUsername()),
//  //        () -> Mockito.verify(member, Mockito.times(3)).checkPassword(member.getPassword())
//  //    );
//  //  }
//
//  @Test
//  public void register() {
//    assertAll(
//        () -> assertTrue(true)
//        //        () -> assertEquals(member, memberUCC.register(member)),
//        //        () -> Mockito.verify(member, Mockito.times(2)).hashPassword(member.getPassword()),
//        //        () -> Mockito.verify(memberDao).register(member)
//    );
//  }
//
//  @Test
//  public void getItem() {
//    assertAll(
//        () -> assertEquals(itemDTO, itemUCC.getItem(itemDTO.getIdItem()))
//    );
//  }
//
//  @Test
//  public void getGivenItems() {
//    items.add(itemDTO);
//    assertEquals(items, itemUCC.getGivenItems());
//  }
//
//  @DisplayName("Tests OfferUCC")
//  @Test
//  public void getOffer() {
//    assertAll(
//        () -> assertEquals(offerDTO, offerUCC.getOffer(offerDTO.getIdOffer()))
//    );
//  }
//
//}