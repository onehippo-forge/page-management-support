/*
 * Copyright 2015 Hippo B.V. (http://www.onehippo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onehippo.forge.channelmanager.pagesupport.channel.event;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.hippoecm.hst.util.NodeUtils;
import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.api.HippoNodeType;

public class HippoFolderDocumentUtils {

    private HippoFolderDocumentUtils() {
    }

    public static boolean folderExists(final Session session, final String folderLocation) throws RepositoryException {
        if (!session.nodeExists(folderLocation)) {
            return false;
        }

        final Node node = session.getNode(folderLocation);

        if (NodeUtils.isNodeType(node, HippoStdNodeType.NT_FOLDER)) {
            return true;
        }

        return false;
    }

    public static boolean documentExists(final Session session, final String documentLocation) throws RepositoryException {
        if (!session.nodeExists(documentLocation)) {
            return false;
        }

        final Node node = session.getNode(documentLocation);

        if (NodeUtils.isNodeType(node, HippoNodeType.NT_HANDLE)) {
            return true;
        } else if (NodeUtils.isNodeType(node, HippoNodeType.NT_DOCUMENT)) {
            if (!session.getRootNode().isSame(node)) {
                Node parentNode = node.getParent();

                if (NodeUtils.isNodeType(parentNode, HippoNodeType.NT_HANDLE)) {
                    return true;
                }
            }
        }

        return false;
    }
}