package nextflow.cloud.azure.batch

import java.nio.file.Path
import java.time.OffsetDateTime

import com.azure.storage.blob.BlobContainerClient
import com.azure.storage.blob.sas.BlobContainerSasPermission
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues
import groovy.transform.CompileStatic
import nextflow.cloud.azure.nio.AzPath
import nextflow.util.Duration
/**
 * Azure helper functions
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
@CompileStatic
class AzHelper {

    static private AzPath az0(Path path){
        if( path !instanceof AzPath )
            throw new IllegalArgumentException("Not a valid Azure path: $path [${path?.getClass()?.getName()}]")
        return (AzPath)path
    }

    static String toHttpUrl(Path path, String sas=null) {
        def url = az0(path).blobClient().getBlobUrl()
        url = URLDecoder.decode(url, 'UTF-8').stripEnd('/')
        return !sas ? url : "${url}?${sas}"
    }

    static String toContainerUrl(Path path, String sas) {
        def url = az0(path).containerClient().getBlobContainerUrl()
        url = URLDecoder.decode(url, 'UTF-8').stripEnd('/')
        return !sas ? url : "${url}?${sas}"
    }

    static String generateContainerSas(Path path, Duration duration) {
        generateSas(az0(path).containerClient(), duration)
    }

    static BlobContainerSasPermission CONTAINER_PERMS = new BlobContainerSasPermission()
            .setAddPermission(true)
            .setCreatePermission(true)
            .setDeletePermission(true)
            .setListPermission(true)
            .setMovePermission(true)
            .setReadPermission(true)
            .setTagsPermission(true)
            .setWritePermission(true)

    static String generateSas(BlobContainerClient client, Duration duration) {
        final now = OffsetDateTime .now()

        final signature = new BlobServiceSasSignatureValues()
                .setPermissions(CONTAINER_PERMS)
                .setStartTime(now)
                .setExpiryTime( now.plusSeconds(duration.toSeconds()) )

        return client .generateSas(signature)
    }

}
