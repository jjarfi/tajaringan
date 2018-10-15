/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sistem;

import java.util.List;

/**
 *
 * @author pacevil
 */
@FunctionalInterface
public interface FileSystem extends SystemCommandHandler
{
    /**
     * Partitions list of the system containing all the drives.
     * their name, size, used space and available space.
     *
     * @return the list of partition items.
     */
    List<FileSystemItem> partitions ();
}