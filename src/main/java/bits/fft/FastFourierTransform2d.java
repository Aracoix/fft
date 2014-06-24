/*
 * Copyright (c) 2014, Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */
package bits.fft;

/**
 * Performs a Fast Fourier Transform on a square matrix of values.
 * Compatible with both real and complex valued inputs.
 * <p>
 * Not thread safe.
 *
 * @author Philip DeCamp
 */
public class FastFourierTransform2d {

    private static final int MAX_BITS = 15;
    private static final int REVERSE_TABLE[] = {
            0x00, 0x80, 0x40, 0xC0, 0x20, 0xA0, 0x60, 0xE0, 0x10, 0x90, 0x50, 0xD0, 0x30, 0xB0, 0x70, 0xF0,
            0x08, 0x88, 0x48, 0xC8, 0x28, 0xA8, 0x68, 0xE8, 0x18, 0x98, 0x58, 0xD8, 0x38, 0xB8, 0x78, 0xF8,
            0x04, 0x84, 0x44, 0xC4, 0x24, 0xA4, 0x64, 0xE4, 0x14, 0x94, 0x54, 0xD4, 0x34, 0xB4, 0x74, 0xF4,
            0x0C, 0x8C, 0x4C, 0xCC, 0x2C, 0xAC, 0x6C, 0xEC, 0x1C, 0x9C, 0x5C, 0xDC, 0x3C, 0xBC, 0x7C, 0xFC,
            0x02, 0x82, 0x42, 0xC2, 0x22, 0xA2, 0x62, 0xE2, 0x12, 0x92, 0x52, 0xD2, 0x32, 0xB2, 0x72, 0xF2,
            0x0A, 0x8A, 0x4A, 0xCA, 0x2A, 0xAA, 0x6A, 0xEA, 0x1A, 0x9A, 0x5A, 0xDA, 0x3A, 0xBA, 0x7A, 0xFA,
            0x06, 0x86, 0x46, 0xC6, 0x26, 0xA6, 0x66, 0xE6, 0x16, 0x96, 0x56, 0xD6, 0x36, 0xB6, 0x76, 0xF6,
            0x0E, 0x8E, 0x4E, 0xCE, 0x2E, 0xAE, 0x6E, 0xEE, 0x1E, 0x9E, 0x5E, 0xDE, 0x3E, 0xBE, 0x7E, 0xFE,
            0x01, 0x81, 0x41, 0xC1, 0x21, 0xA1, 0x61, 0xE1, 0x11, 0x91, 0x51, 0xD1, 0x31, 0xB1, 0x71, 0xF1,
            0x09, 0x89, 0x49, 0xC9, 0x29, 0xA9, 0x69, 0xE9, 0x19, 0x99, 0x59, 0xD9, 0x39, 0xB9, 0x79, 0xF9,
            0x05, 0x85, 0x45, 0xC5, 0x25, 0xA5, 0x65, 0xE5, 0x15, 0x95, 0x55, 0xD5, 0x35, 0xB5, 0x75, 0xF5,
            0x0D, 0x8D, 0x4D, 0xCD, 0x2D, 0xAD, 0x6D, 0xED, 0x1D, 0x9D, 0x5D, 0xDD, 0x3D, 0xBD, 0x7D, 0xFD,
            0x03, 0x83, 0x43, 0xC3, 0x23, 0xA3, 0x63, 0xE3, 0x13, 0x93, 0x53, 0xD3, 0x33, 0xB3, 0x73, 0xF3,
            0x0B, 0x8B, 0x4B, 0xCB, 0x2B, 0xAB, 0x6B, 0xEB, 0x1B, 0x9B, 0x5B, 0xDB, 0x3B, 0xBB, 0x7B, 0xFB,
            0x07, 0x87, 0x47, 0xC7, 0x27, 0xA7, 0x67, 0xE7, 0x17, 0x97, 0x57, 0xD7, 0x37, 0xB7, 0x77, 0xF7,
            0x0F, 0x8F, 0x4F, 0xCF, 0x2F, 0xAF, 0x6F, 0xEF, 0x1F, 0x9F, 0x5F, 0xDF, 0x3F, 0xBF, 0x7F, 0xFF
    };

    private final int mDim;
    private final int mBits;

    private final double[] mWork;


    /**
     * The sole argument, <tt>dim</tt>, indicates the size
     * of one side of square matrix on which the transform will
     * operate. This must be a power-of-two. For instance, if
     * <tt>dim = 4</tt>, then this transform will accept
     * as input a matrix of size [4,4] and write to an
     * output matrix of size [4,4].
     * <p>
     * Memory footprint is just over 16 * dim * dim bytes.
     *
     * @param dim Size of one side of square matrix on which the transform operates. Must be power-of-two.
     * @throws IllegalArgumentException if dim is smaller than 2, too large, or not a power-of-two.
     */
    public FastFourierTransform2d( int dim ) {
        mDim  = dim;
        mBits = computeBitNum( dim );
        mWork = new double[dim * dim * 2];
    }


    /**
     * Performs a 2D Fast Fourier Transform on a square matrix of complex values.
     * <p>
     * Complex samples must be stored in a tightly packed format.  For a 2x2 matrix: <br>
     * <tt>[... r_0_0, i_0_0, r_1_0, i_1_0, r_0_1, i_0_1, r_1_1, i_1_1 ...] </tt><br>,
     * where <tt>r_m_n</tt> is the real component of element at position [m_n] and
     * <tt>ik</tt> is the corresponding imaginary component.
     *
     * @param x       Input array of complex values.  <tt>x.length &gt= dim * dim * 2 + xOff</tt>.
     * @param xOff    Start position of data in the input array.
     * @param inverse Set to false for normal FFT, true for inverse FFT.
     * @param out     Output array where transformed, complex elements are stored. <tt>out.length &gt= dim*dim*2 + outOff</tt>.
     * @param outOff  Start position into output array.
     */
    public void applyComplex( double[] x, int xOff, boolean inverse, double[] out, int outOff ) {
        final int dim = mDim;
        final int dim2 = mDim * 2;
        final int len = dim2 * dim;
        final int shift = 30 - mBits;

        for( int xa = 0; xa < dim2; xa += 2 ) {
            final int ia = xa + xOff;
            final int ib = (reverse( xa ) >>> shift) + outOff;

            for( int y = 0; y < len; y += dim2 ) {
                out[ib + y    ] = x[ia + y    ];
                out[ib + y + 1] = x[ia + y + 1];
            }
        }

        applyTheRest( out, outOff, inverse );
    }

    /**
     * Performs a 2D Fast Fourier Transform on a square matrix of real values.  NOTE that
     * output is COMPLEX.
     * <p>
     * Samples must be stored in a tightly packed format.  For a 2x2 matrix: <br>
     * <tt>[... r_0_0, r_1_0, r_0_1, r_1_1, ...] </tt><br>,
     * where <tt>r_m_n</tt> is the real-valued element at position [m,n].
     *
     * @param x       Input array of real values.  <tt>x.length &gt= dim * dim + xOff</tt>.
     * @param xOff    Start position of data in the input array.
     * @param inverse Set to false for normal FFT, true for inverse FFT.
     * @param out     Output array where transformed, complex elements are stored. <tt>out.length &gt= dim*dim*2 + outOff</tt>.
     * @param outOff  Start position into output array.
     */
    public void applyReal( double[] x, int xOff, boolean inverse, double[] out, int outOff ) {
        // Bit-reverse the order of the data and copy into the output array.
        final int shift = 31 - mBits;
        final int dim = mDim;
        final int len = dim * dim;

        for( int i = 0; i < dim; i++ ) {
            final int indOut = (reverse( i ) >>> shift) + outOff;
            final int indX = i + xOff;

            for( int j = 0; j < len; j += dim ) {
                out[indOut + j * 2    ] = x[indX + j];
                out[indOut + j * 2 + 1] = 0.0;
            }
        }

        applyTheRest( out, outOff, inverse );
    }



    static int computeBitNum( int n ) {
        int bits = 31 - Integer.numberOfLeadingZeros( n );
        if( bits <= 0 || bits >= MAX_BITS || 1 << bits != n ) {
            throw new IllegalArgumentException( "Dimension must be a power of two, larger than 1, and smaller than 1 << " + MAX_BITS );
        }
        return bits;
    }

    /**
     * @return Version of val with reversed bits.
     */
    static int reverse( int val ) {
        return ( REVERSE_TABLE[   val         & 0xff] << 24) |
               ( REVERSE_TABLE[ ( val >>  8 ) & 0xff] << 16) |
               ( REVERSE_TABLE[ ( val >> 16 ) & 0xff] <<  8) |
               ( REVERSE_TABLE[ ( val >> 24 ) & 0xff]      );
    }


    static void transform( double[] x, int off, int len, boolean inverse ) {
        final double sign = inverse ? -1.0 : 1.0;

        int blockEnd = 1;
        double ar0, ar1, ar2;
        double ai0, ai1, ai2;

        for( int blockSize = 2; blockSize <= len; blockSize <<= 1 ) {
            final double angle = Math.PI * 2.0 / blockSize;
            final double cm1   = Math.cos( angle );

            //Use trig identity to quickly compute: sm1 = Math.sin(angle)
            //Block size >= 2  ergo   0 <= angle <= Math.PI  ergo  sin(angle) >= 0   
            final double sm1 = sign * Math.sqrt( 1.0 - cm1 * cm1 );

            //Use trig identity to compute: cm2 = Math.cos(2.0 * angle)
            final double cm2 = 2.0 * cm1 * cm1 - 1.0;

            //Use trig identity to compute: sm2 = Math.sin(2.0 * angle);
            final double sm2 = 2.0 * sm1 * cm1;

            final double w = 2.0 * cm1;

            for( int i = 0; i < len; i += blockSize ) {
                ar2 = cm2;
                ar1 = cm1;

                ai2 = sm2;
                ai1 = sm1;

                for( int j = i * 2 + off, n = 0; n < blockEnd; j += 2, n++ ) {
                    ar0 = w * ar1 - ar2;
                    ar2 = ar1;
                    ar1 = ar0;

                    ai0 = w * ai1 - ai2;
                    ai2 = ai1;
                    ai1 = ai0;

                    for( int s = 0; s < len; s++ ) {
                        int aa = j + s * len * 2;
                        int bb = aa + blockEnd * 2;

                        double tr = ar0 * x[bb    ] - ai0 * x[bb + 1];
                        double ti = ar0 * x[bb + 1] + ai0 * x[bb    ];

                        x[bb    ] = x[aa    ] - tr;
                        x[bb + 1] = x[aa + 1] - ti;

                        x[aa    ] += tr;
                        x[aa + 1] += ti;
                    }
                }
            }

            blockEnd = blockSize;
        }
    }



    private void applyTheRest( double[] a, int aOff, boolean inverse ) {
        transform( a, aOff, mDim, inverse );
        shuffle1( a, aOff, mDim, mBits, mWork, 0 );
        transform( mWork, 0, mDim, inverse );
        if( !inverse ) {
            shuffle2( mWork, 0, mDim, a, aOff );
        } else {
            invShuffle2( mWork, 0, mDim, a, aOff );
        }
    }

    /**
     * 1. Transpose without conjugation.
     * 2. Bit-reversal shuffle.
     */
    private static void shuffle1( double[] a, int offA, int dim, int bits, double[] out, int offOut ) {
        final int dim2 = dim * 2;
        final int shift = 30 - bits;

        for( int y = 0; y < dim2; y += 2 ) {
            int ia = y * dim + offA;
            int ib = (reverse( y ) >>> shift) + offOut;

            for( int x = 0; x < dim2; x += 2 ) {
                out[ib + x * dim] = a[ia + x];
                out[ib + x * dim + 1] = a[ia + x + 1];
            }
        }
    }

    /**
     * 1. Transpose without conjugation.
     */
    private static void shuffle2( double[] a, int offA, int dim, double[] out, int offOut ) {
        final int dim2 = dim * 2;

        for( int y = 0; y < dim2; y += 2 ) {
            int ia = y + offA;
            int ib = y * dim + offOut;

            for( int x = 0; x < dim2; x += 2 ) {
                out[ib + x] = a[ia + x * dim];
                out[ib + x + 1] = a[ia + x * dim + 1];
            }
        }
    }

    /**
     * 1. Scale
     * 2. Transpose without conjugation.
     */
    private static void invShuffle2( double[] a, int offA, int dim, double[] out, int offOut ) {
        final double scale = 1.0 / ( dim * dim );
        final int    dim2  = dim * 2;

        for( int y = 0; y < dim2; y += 2 ) {
            int ia = y + offA;
            int ib = y * dim + offOut;

            for( int x = 0; x < dim2; x += 2 ) {
                out[ib + x    ] = a[ia + x * dim    ] * scale;
                out[ib + x + 1] = a[ia + x * dim + 1] * scale;
            }
        }
    }

}