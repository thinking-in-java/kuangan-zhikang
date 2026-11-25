package com.yihe.framework.ehr;
import org.springframework.stereotype.Service;
@Service
public class GeneralArchiveService extends OccupationalArchiveService {
 public GeneralArchiveService(EhrRepository db,EhrService ehr,EmployeeHealthArchiveService employees){super(db,ehr,employees);}
 @Override String table(){return "a_general_archive_versions";}
 @Override boolean occupational(){return false;}
}
