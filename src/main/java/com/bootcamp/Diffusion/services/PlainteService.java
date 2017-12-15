package com.bootcamp.Diffusion.services;

import com.bootcamp.client.OctoPushClient;
import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.crud.EtapeCRUD;
import com.bootcamp.crud.PlainteCRUD;
import com.bootcamp.crud.WorkFlowCRUD;
import com.bootcamp.entities.Etape;
import com.bootcamp.entities.Plainte;
import com.bootcamp.entities.WorkFlow;
import java.io.IOException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Bignon on 14/12/17.
 */
@Component
public class PlainteService implements DatabaseConstants {


    public Plainte createPlainte(Plainte plainte) throws SQLException, IOException {
        plainte.setDateCreation(System.currentTimeMillis());
        plainte.setDateMiseAJour(System.currentTimeMillis());
        plainte.setEtatValidation(false);
        UUID ref = UUID.randomUUID();
        plainte.setReference(ref.toString());

        PlainteCRUD.create(plainte);

        return plainte;
    }

    public Etape createEtape(Etape etape) throws SQLException, IOException {

        EtapeCRUD.create(etape);
        return etape;
    }

    public WorkFlow createWorFlow(WorkFlow workFlow) throws SQLException, IOException {

        WorkFlowCRUD.create(workFlow);
        return workFlow;
    }

    public Plainte sendPlainte(Plainte plainte) throws SQLException,IOException {
        Plainte plainte1 = this.createPlainte(plainte);
        OctoPushClient octopushClient = new OctoPushClient();
        octopushClient.sendSms(plainte1.getContenu()+"\n Votre Ref#: "+plainte1.getReference());


        return plainte1;
    }


//    public Plainte update(Plainte plainte) throws SQLException {
//        plainte.setDateMiseAJour(System.currentTimeMillis());
//        PlainteCRUD.update(plainte);
//
//        return plainte;
//    }

//    public Plainte delete(int id) throws SQLException {
//        Plainte plainte = read(id);
//        PlainteCRUD.delete(plainte);
//
//        return plainte;
//    }

    public Plainte readPlainte(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Plainte> plaintes = PlainteCRUD.read(criterias);

        return plaintes.get(0);
    }

    public Etape readEtape(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Etape> etapes = EtapeCRUD.read(criterias);

        return etapes.get(0);
    }

    public Plainte readPlainteByReference(String reference) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("reference", "=", reference));
        List<Plainte> plaintes = PlainteCRUD.read(criterias);

        return plaintes.get(0);
    }

    public WorkFlow readWorkFlow(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<WorkFlow> workFlows = WorkFlowCRUD.read(criterias);

        return workFlows.get(0);
    }

    public List<WorkFlow> readWorkFlowByIdPlainte(int id) throws SQLException {

        Rule rule1 = new Rule();
        rule1.setEntityClass(WorkFlow.class);
        rule1.setEntityClass(WorkFlow.class);
        rule1.setColumn("idPlainte");
        rule1.setOperator("=");
        rule1.setValue(id);
        Criteria criteria = new Criteria();
        criteria.setRule(rule1);

        Criterias criterias = new Criterias();
        criterias.addCriteria(criteria);
        List<WorkFlow> workFlows = WorkFlowCRUD.read(criterias);

        return workFlows;
    }

    public List<WorkFlow> readWorkFlowByIdEtape(int id) throws SQLException {

        Rule rule1 = new Rule();
        rule1.setEntityClass(WorkFlow.class);
        rule1.setEntityClass(WorkFlow.class);
        rule1.setColumn("idEtape");
        rule1.setOperator("=");
        rule1.setValue(id);
        Criteria criteria = new Criteria();
        criteria.setRule(rule1);

        Criterias criterias = new Criterias();
        criterias.addCriteria(criteria);
        List<WorkFlow> workFlows = WorkFlowCRUD.read(criterias);

        return workFlows;
    }

    public List<Plainte> readPlainte() throws SQLException {
        List<Plainte> plaintes = PlainteCRUD.read();
        return plaintes;
    }

    public List<Etape> readEtape() throws SQLException {
        List<Etape> etapes = EtapeCRUD.read();
        return etapes;
    }

    public List<WorkFlow> readWorkFlow() throws SQLException {
        List<WorkFlow> workFlows = WorkFlowCRUD.read();
        return workFlows;
    }

}
