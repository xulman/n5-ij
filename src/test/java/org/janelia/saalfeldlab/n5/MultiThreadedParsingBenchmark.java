package org.janelia.saalfeldlab.n5;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.janelia.saalfeldlab.n5.dataaccess.DataAccessException;
import org.janelia.saalfeldlab.n5.ij.N5Factory;
import org.janelia.saalfeldlab.n5.ij.N5Importer;

public class MultiThreadedParsingBenchmark
{

	public static void main( String[] args ) throws IOException, DataAccessException
	{
//		ExecutorService executor = Executors.newSingleThreadExecutor();
//		ExecutorService executor = Executors.newFixedThreadPool( 16 );
		final ExecutorService executor = Executors.newCachedThreadPool();

        final String n5RootPath = args[ 0 ];
        final String n5Dataset = args[ 1 ];

		final N5Reader n5 = new N5Factory().openReader( n5RootPath );
		final N5DatasetDiscoverer discoverer = new N5DatasetDiscoverer( n5, executor, null, 
				N5DatasetDiscoverer.fromParsers( N5Importer.PARSERS ) );

		long start = System.currentTimeMillis();
		System.out.println( "discover" );
		final N5TreeNode root = discoverer.discoverAndParseRecursive(n5Dataset);
		
		long end = System.currentTimeMillis();

		System.out.println( "parsing took " + (end-start) + " ms");
	}

}
