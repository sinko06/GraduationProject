package com.example.gogoooma.graduationproject;

import java.io.File;
import java.io.FileNotFoundException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.util.fft.FFT;

public class AmpZero {
    int k;
    float sum;
    float in[][];
    int count = 0;
    public float[][] Test (final String str) throws FileNotFoundException {

        in = new float[1][1000];
            File file = new File(str);
            sum = 0;
            k=0;
            final int bufferSize = 4096;
            final int fftSize = bufferSize / 2;
            final int sampleRate = 44100;
            AudioDispatcher audioDispatcher;
            audioDispatcher = AudioDispatcherFactory.fromPipe(file.getAbsolutePath(), sampleRate, bufferSize, 0);
            audioDispatcher.addAudioProcessor(new AudioProcessor() {

                FFT fft = new FFT(bufferSize);
                final float[] amplitudes = new float[fftSize];
                public boolean process(AudioEvent audioEvent) {
                    float[] audioBuffer = audioEvent.getFloatBuffer();
                    fft.forwardTransform(audioBuffer);
                    fft.modulus(audioBuffer, amplitudes);
                    for (int j = 0; j < amplitudes.length; j++) {
                        sum = sum + amplitudes[j];
                        k++;
                        if(k<=1000000 && k%1000==0){
                            in[0][k/1000-1] = sum/1000;
                            sum = 0;
                        }
                    }
                    return true;
                }

                @Override
                public void processingFinished() {

                }
            });
            audioDispatcher.run();
            return in;
    }

}

