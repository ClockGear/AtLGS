import domain.Format;
import enums.Game;
import service.FormatService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import static javax.ejb.TransactionManagementType.BEAN;

/**
 * Created by Dennis van Opstal on 12-1-2018.
 */
@Startup
@Singleton
@TransactionManagement(BEAN)
public class StartupBean {

    @Inject
    private FormatService formatService;

    @PostConstruct
    private void startup() {
        //region MtG Formats
        Format standard = new Format("Standard", Game.MTG, "STD");
        formatService.create(standard);
        Format modern = new Format("Modern", Game.MTG, "MDN");
        formatService.create(modern);
        Format commander = new Format("Commander", Game.MTG, "EDH");
        formatService.create(commander);
        Format pauper = new Format("Pauper", Game.MTG, "PAU");
        formatService.create(pauper);
        Format legacy = new Format("Legacy", Game.MTG, "LEA");
        formatService.create(legacy);
        Format vintage = new Format("Vintage", Game.MTG, "VIN");
        formatService.create(vintage);
        Format casual = new Format("Casual", Game.MTG, "CAS");
        formatService.create(casual);
        Format league = new Format("League", Game.MTG, "LEA");
        formatService.create(league);
        Format frontier = new Format("Frontier", Game.MTG, "FRO");
        formatService.create(frontier);
        Format tinyLeaders = new Format("Tiny Leaders", Game.MTG, "TLD");
        formatService.create(tinyLeaders);
        Format blockConstructed = new Format("Block Constructed", Game.MTG, "BCS");
        formatService.create(blockConstructed);
        Format canadianHighlander = new Format("Canadian Highlander", Game.MTG, "CAN");
        formatService.create(canadianHighlander);
        Format draft = new Format("Draft", Game.MTG, "DRA");
        formatService.create(draft);
        Format sealed = new Format("Sealed", Game.MTG, "SEA");
        formatService.create(sealed);
        Format other = new Format("Other Format", Game.MTG, "OTH");
        formatService.create(other);
        //endregion
    }
}