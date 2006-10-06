/**
 * $Revision: $
 * $Date: $
 *
 * Copyright (C) 2006 Jive Software. All rights reserved.
 *
 * This software is published under the terms of the GNU Lesser Public License (LGPL),
 * a copy of which is included in this distribution.
 */

package org.jivesoftware.sparkimpl.search.users;

import org.jivesoftware.resource.Res;
import org.jivesoftware.resource.SparkRes;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.packet.DiscoverInfo;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.search.Searchable;
import org.jivesoftware.spark.ui.DataFormUI;
import org.jivesoftware.spark.util.GraphicUtils;
import org.jivesoftware.spark.util.SwingWorker;
import org.jivesoftware.spark.util.log.Log;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UserSearchService implements Searchable {
    private Collection searchServices;

    public UserSearchService() {

    }

    public void search(final String query) {
        SwingWorker worker = new SwingWorker() {
            public Object construct() {
                // On initialization, find search service.
                if (searchServices == null) {
                    loadSearchServices();
                }
                return true;
            }

            public void finished() {
                processQuery(query);
            }
        };

        worker.start();

    }

    private void processQuery(String query) {
        if (searchServices == null) {
            JOptionPane.showMessageDialog(SparkManager.getMainWindow(), Res.getString("message.search.service.not.available"), Res.getString("title.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserSearchForm searchForm = null;
        DataFormUI dataFormUI = null;
        try {
            searchForm = new UserSearchForm(searchServices);
            dataFormUI = searchForm.getQuestionForm();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(SparkManager.getMainWindow(), Res.getString("message.search.service.not.available"), Res.getString("title.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField textField = (JTextField)dataFormUI.getComponent("search");
        if (textField != null) {
            textField.setText(query);
        }
        else {
            textField = (JTextField)dataFormUI.getComponent("last");
            if (textField != null) {
                textField.setText(query);
            }
        }

        if (textField == null) {
            textField = (JTextField)dataFormUI.getComponent("userName");
            if (textField != null) {
                textField.setText(query);
            }
        }

        if (textField != null) {
            searchForm.performSearch();
        }

        JFrame frame = new JFrame();
        frame.setIconImage(SparkRes.getImageIcon(SparkRes.VIEW_IMAGE).getImage());
        final JDialog dialog = new JDialog(frame, Res.getString("title.person.search"), false);
        dialog.getContentPane().add(searchForm);
        dialog.pack();
        dialog.setSize(500, 500);

        GraphicUtils.centerWindowOnScreen(dialog);
        dialog.setVisible(true);
    }

    /**
     * Load all Search Services.
     */
    private void loadSearchServices() {
        try {
            searchServices = getServices();
        }
        catch (Exception e) {
            Log.error("Unable to load search services.", e);
        }
    }

    /**
     * Returns a collection of search services found on the server.
     *
     * @return a Collection of search services found on the server.
     * @throws XMPPException thrown if a server error has occurred.
     */
    private Collection getServices() throws Exception {
        final List<String> searchServices = new ArrayList<String>();
        ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(SparkManager.getConnection());
        DiscoverItems items = SparkManager.getSessionManager().getDiscoveredItems();
        Iterator<DiscoverItems.Item> iter = items.getItems();
        while (iter.hasNext()) {
            DiscoverItems.Item item = iter.next();
            try {
                DiscoverInfo info;
                try {
                    info = discoManager.discoverInfo(item.getEntityID());
                }
                catch (XMPPException e) {
                    // Ignore Case
                    continue;
                }

                if (info.containsFeature("jabber:iq:search")) {
                    searchServices.add(item.getEntityID());
                }
            }
            catch (Exception e) {
                // No info found.
                break;
            }
        }
        return searchServices;
    }

    /**
     * Return the Search Services discovered by the client.
     *
     * @return the discovered search services.
     */
    public Collection getSearchServices() {
        return searchServices;
    }

    public String getToolTip() {
        return Res.getString("message.search.for.other.people");
    }

    public String getDefaultText() {
        return Res.getString("message.search.for.other.people");
    }

    public String getName() {
        return Res.getString("title.person.search");
    }

    public Icon getIcon() {
        return SparkRes.getImageIcon(SparkRes.SEARCH_USER_16x16);
    }


}
