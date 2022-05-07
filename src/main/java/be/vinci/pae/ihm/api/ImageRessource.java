package be.vinci.pae.ihm.api;

import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Path("/images")
public class ImageRessource {

  @Inject
  ItemUCC itemUcc;
  //private static final String[] VALID_EXTENSIONS = {"jpg","png","jpeg"};


  /**
   * Upload an image.
   *
   * @param file file as InputStream
   * @param fileDisposition informations of the file
   * @return build
   */
  @POST
  @Path("/upload{idItem}")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public void uploadFile(@FormDataParam("file") InputStream file, @PathParam("idItem") int idItem,
                             @FormDataParam("file") FormDataContentDisposition fileDisposition) throws IOException {
    String fileExtension = FilenameUtils.getExtension(fileDisposition.getFileName());
    /*if (fileExtension != "png" || fileExtension != "jpg" || fileExtension != "jpeg"){
      throw new IllegalArgumentException("Not the correct extension");
    }*/
    String fileNameUUID = UUID.randomUUID().toString();
    String photoPath = Config.getProperty("PhotoPath");
    String insertPath = photoPath + "\\" + fileNameUUID + "." + fileExtension;
    // save/copy the file ion the folder (OneDrive)
    Files.copy(file, Paths.get(insertPath));
    // save the file(fileNameUUID + extension, id item) in the db, to get it back after
    itemUcc.insertPhoto(fileNameUUID + "." + fileExtension,idItem);
    // send a message after the event : uplaod a file (logger)
  }

  @GET
  @Path("/{photo}")
  public File getPhotoPath(@PathParam("photo") String photoName){
    //System.out.print("\nPasser par laaaaa : photoPath");
    String path =  Config.getProperty("PhotoPath");
    File file = new File(path+ "\\" + photoName);
    //System.out.print(path + "\\" + photoName);
    return file;
  }
}
