/*
 * Copyright © 2009-2018 The Apromore Initiative.
 *
 * This file is part of "Apromore".
 *
 * "Apromore" is free software; you can redistribute it and/or modify
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

package org.apromore.portal.common;

import org.apromore.dao.model.Group;
import org.apromore.dao.model.User;
import org.apromore.exception.UserNotFoundException;
import org.apromore.manager.client.ManagerService;
import org.apromore.model.FolderType;
import org.apromore.model.GroupAccessType;
import org.apromore.model.LogSummaryType;
import org.apromore.model.ProcessSummaryType;
import org.apromore.model.SummaryType;
import org.apromore.portal.dialogController.SecuritySetupController;
import org.apromore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import java.util.Collections;
import java.util.List;

/**
 * Handles the item render for the Folder Tree list.
 * @author Igor
 */
public class SecurityFolderTreeRenderer implements TreeitemRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFolderTreeRenderer.class.getName());

    private SecuritySetupController securitySetupController;

    public SecurityFolderTreeRenderer(SecuritySetupController securitySetupController) {
        this.securitySetupController = securitySetupController;
    }

    /*
    private SecurityPermissionsController permissionsController;

    public SecurityFolderTreeRenderer() {
    }

    public SecurityFolderTreeRenderer(SecurityPermissionsController permissionsController) {
        this.permissionsController = permissionsController;
    }

    public void setController(SecurityPermissionsController permissionsController) {
        this.permissionsController = permissionsController;
    }
    */

    @Override
    public void render(final Treeitem treeItem, Object treeNode, int i) throws Exception {
        FolderTreeNode ctn = (FolderTreeNode) treeNode;

        Treerow dataRow = new Treerow();
        dataRow.setParent(treeItem);
        treeItem.setValue(ctn);
        treeItem.setOpen(true);

        Hlayout hl = new Hlayout();

        SummaryType summaryType;

        switch (ctn.getType()) {
        case Folder:
            FolderType folder = (FolderType) ctn.getData();
            FolderType currentFolder = UserSessionManager.getCurrentFolder();

            if (folder.getParentId() == null || folder.getParentId() == 0 || checkOpenFolderTree(folder, currentFolder)) {
                treeItem.setOpen(true);
                if (currentFolder != null && folder.getId().equals(currentFolder.getId())) {
                    treeItem.setSelected(true);
                }
            } else {
                treeItem.setOpen(false);
            }

            hl.appendChild(new Image(folder.getId() == 0 ? "/img/home-folder24.png" : "/img/folder24.png"));
            String folderName = folder.getFolderName();
            hl.appendChild(new Label(folderName.length() > 15 ? folderName.substring(0, 13) + "..." : folderName));
            break;

        case Process:
            summaryType = (SummaryType) ctn.getData();
            if(summaryType instanceof ProcessSummaryType) {
                ProcessSummaryType process = (ProcessSummaryType) summaryType;
                hl.appendChild(new Image("/img/process24.png"));
                String processName = process.getName();
                hl.appendChild(new Label(processName.length() > 15 ? processName.substring(0, 13) + "..." : processName));
            }
            break;

        case Log:
            summaryType = (SummaryType) ctn.getData();
            if(summaryType instanceof LogSummaryType) {
                LogSummaryType log = (LogSummaryType) summaryType;
                hl.appendChild(new Image("/img/icon/svg/log_icon.svg"));
                String processName = log.getName();
                hl.appendChild(new Label(processName.length() > 15 ? processName.substring(0, 13) + "..." : processName));
            }
            break;

        default:
            assert false: "Folder tree node with type " + ctn.getType() + " is not implemented";
        }

        hl.setSclass("h-inline-block");
        Treecell treeCell = new Treecell();
        treeCell.appendChild(hl);
        dataRow.appendChild(treeCell);

        dataRow.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                FolderTreeNode clickedNodeValue = ((Treeitem) event.getTarget().getParent()).getValue();

                try {
                    int selectedId = 0;
                    List<GroupAccessType> groups = Collections.emptyList();

                    ManagerService service = (ManagerService) SpringUtil.getBean("managerClient");
                    switch (clickedNodeValue.getType()) {
                    case Folder:
                        FolderType selectedFolder = (FolderType) clickedNodeValue.getData();
                        selectedId = selectedFolder.getId();
                        if (selectedFolder.getId() == 0) {
                            // Nobody can edit the permissions of the root folder "Home"
                        } else {
                            groups = service.getFolderGroups(selectedId);
                        }
                        break;
  
                    case Process:
                        SummaryType summaryType = (SummaryType) clickedNodeValue.getData();
                        if(summaryType instanceof ProcessSummaryType) {
                            ProcessSummaryType process = (ProcessSummaryType) summaryType;
                            selectedId = process.getId();
                            groups = service.getProcessGroups(selectedId);
                        }
                        break;

                    case Log:
                        SummaryType lsummaryType = (SummaryType) clickedNodeValue.getData();
                        if(lsummaryType instanceof LogSummaryType) {
                            LogSummaryType log = (LogSummaryType) lsummaryType;
                            selectedId = log.getId();
                            groups = service.getLogGroups(selectedId);
                        }
                        break;

                    default:
                        assert false: "Clicked tree node with type " + clickedNodeValue.getType() + " is not implemented";
                    }

                    UserSessionManager.setCurrentSecurityOwnership(currentUserHasOwnership(groups));
                    UserSessionManager.setCurrentSecurityItem(selectedId);
                    UserSessionManager.setCurrentSecurityType(clickedNodeValue.getType());
                    if (securitySetupController != null) {
                        securitySetupController.getPermissionsController().loadUsers(selectedId, clickedNodeValue.getType());
                        securitySetupController.getFindGroupsController().updateSelection();
                    }
                } catch (Exception ex) {
                    LOGGER.error("SecurityFolderTree Renderer failed to render an item", ex);
                }
            }
        });
    }


    /**
     * @param groups  permission groups (typically for a folder, log, or process model)
     * @return whether the <var>groups</var> grant ownership to the current user
     */
    private boolean currentUserHasOwnership(List<GroupAccessType> groups) {
        try {
            UserService userService = (UserService) SpringUtil.getBean("userService");
            User user = userService.findUserByLogin(UserSessionManager.getCurrentUser().getUsername());
            for (final GroupAccessType group: groups) {
                if (group.isHasOwnership()) {
                    for (final Group userGroup: user.getGroups()) {
                        if (userGroup.getName().equals(group.getName())) {
                            return true;
                        }
                    }
                }
            }

        } catch (UserNotFoundException e) {
            LOGGER.error("Unrecognized current user", e);
        }

        return false;
    }


    /**
     * Check the folder tree and make sure we return true if we are looking at a folder that is opened by a user.
     * Could be multiple levels down the tree.
     *
     * @param folder  a folder to search for
     * @param currentFolder  the root of a folder tree to search within
     * @return whether the <var>folder</var> is present within the folder tree rooted at <var>currentFolder</var>
     */
    private boolean checkOpenFolderTree(FolderType folder, FolderType currentFolder) {
        return checkDownTheFolderTree(Collections.singletonList(folder), currentFolder);
    }


    private boolean checkDownTheFolderTree(List<FolderType> subFolders, FolderType currentFolder) {
        for (FolderType folderType : subFolders) {
            if (folderType.getId().equals(currentFolder.getId())) { return true; }
        }
        for (FolderType folderType : subFolders) {
            if (checkDownTheFolderTree(folderType.getFolders(), currentFolder)) { return true; }
        }
        return false;
    }
}
