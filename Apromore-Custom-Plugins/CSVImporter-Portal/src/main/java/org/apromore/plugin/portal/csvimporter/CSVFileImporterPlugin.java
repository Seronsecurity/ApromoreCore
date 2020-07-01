/*-
 * #%L
 * This file is part of "Apromore Core".
 * %%
 * Copyright (C) 2018 - 2020 The University of Melbourne.
 * %%
 * Copyright (C) 2020, Apromore Pty Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * "Apromore" is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.
 * If not, see <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */

package org.apromore.plugin.portal.csvimporter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apromore.plugin.portal.FileImporterPlugin;
import org.apromore.plugin.portal.PortalContext;
import org.apromore.service.csvimporter.CSVImporterLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

public class CSVFileImporterPlugin implements FileImporterPlugin {

    private static Logger LOGGER = LoggerFactory.getLogger(CSVFileImporterPlugin.class);

    private CSVImporterLogic csvImporterLogic;

    public void setCsvImporterLogic(CSVImporterLogic newCSVImporterLogic) {
        LOGGER.info("Injected CSV importer logic {}", newCSVImporterLogic);
        this.csvImporterLogic = newCSVImporterLogic;
    }

    @Override
    public Set<String> getFileExtensions() {
        return Collections.singleton("csv");
    }

    @Override
    public void importFile(Media media, boolean isLogPublic) {

        // Configure the arguments to pass to the CSV importer view
        Map arg = new HashMap<>();
        arg.put("csvImporterLogic", csvImporterLogic);
        arg.put("media", media);

        //TODO: create a dialog to provide options to user: “An existing mapping is applicable to this log. Would you
        // like to use it?” and shows an example with the first 5 rows as a table inside the window.
        // 1. Yes -> import directly
        // 2. Edit -> go to importer view and load the existing mapping (the latest one)
        // 3. No -> Import as a new process -> go to importer view and load guessed mapping

        // Create a CSV importer view
        String zul = "/org/apromore/plugin/portal/csvimporter/csvimporter.zul";
        PortalContext portalContext = (PortalContext) Sessions.getCurrent().getAttribute("portalContext");
        try {
            Window window = (Window) portalContext.getUI().createComponent(getClass().getClassLoader(), zul, null, arg);
            window.doModal();

            // Attempt 1: try to create a popup window on top of csvImporter window here.
            Window matchedMappingPopUp = (Window) portalContext.getUI().createComponent(getClass().getClassLoader(), "zul" +
                    "/matchedMapping.zul", window, null);
            matchedMappingPopUp.doModal();

        } catch (IOException e) {
            LOGGER.error("Unable to create window", e);
        }
    }
}
