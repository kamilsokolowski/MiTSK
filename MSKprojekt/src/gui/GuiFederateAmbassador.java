package gui;

import client.Client;
import hla.rti1516e.*;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.HLAinteger32BE;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.time.HLAfloat64Time;
import org.portico.impl.hla1516e.types.encoding.HLA1516eInteger32BE;
import serviceMan.ServiceMan;

import java.util.Iterator;
import java.util.Map;

public class GuiFederateAmbassador extends NullFederateAmbassador {
    //----------------------------------------------------------
    //                    STATIC VARIABLES
    //----------------------------------------------------------

    //----------------------------------------------------------
    //                   INSTANCE VARIABLES
    //----------------------------------------------------------
    private GuiFederate federate;

    // these variables are accessible in the package
    protected double federateTime        = 0.0;
    protected double federateLookahead   = 1.0;

    protected boolean isRegulating       = false;
    protected boolean isConstrained      = false;
    protected boolean isAdvancing        = false;

    protected boolean isAnnounced        = false;
    protected boolean isReadyToRun       = false;

    protected boolean isRunning       = true;

    //----------------------------------------------------------
    //                      CONSTRUCTORS
    //----------------------------------------------------------

    public GuiFederateAmbassador(GuiFederate federate )
    {
        this.federate = federate;
    }

    //----------------------------------------------------------
    //                    INSTANCE METHODS
    //----------------------------------------------------------
    private void log( String message )
    {
        System.out.println( "FederateAmbassador: " + message );
    }

    //////////////////////////////////////////////////////////////////////////
    ////////////////////////// RTI Callback Methods //////////////////////////
    //////////////////////////////////////////////////////////////////////////
    @Override
    public void synchronizationPointRegistrationFailed( String label,
                                                        SynchronizationPointFailureReason reason )
    {
        log( "Failed to register sync point: " + label + ", reason="+reason );
    }

    @Override
    public void synchronizationPointRegistrationSucceeded( String label )
    {
        log( "Successfully registered sync point: " + label );
    }

    @Override
    public void announceSynchronizationPoint( String label, byte[] tag )
    {
        log( "Synchronization point announced: " + label );
        if( label.equals(GuiFederate.READY_TO_RUN) )
            this.isAnnounced = true;
    }

    @Override
    public void federationSynchronized( String label, FederateHandleSet failed )
    {
        log( "Federation Synchronized: " + label );
        if( label.equals(GuiFederate.READY_TO_RUN) )
            this.isReadyToRun = true;
    }

    /**
     * The RTI has informed us that time regulation is now enabled.
     */
    @Override
    public void timeRegulationEnabled( LogicalTime time )
    {
        this.federateTime = ((HLAfloat64Time)time).getValue();
        this.isRegulating = true;
    }

    @Override
    public void timeConstrainedEnabled( LogicalTime time )
    {
        this.federateTime = ((HLAfloat64Time)time).getValue();
        this.isConstrained = true;
    }

    @Override
    public void timeAdvanceGrant( LogicalTime time )
    {
        this.federateTime = ((HLAfloat64Time)time).getValue();
        this.isAdvancing = false;
    }

    @Override
    public void discoverObjectInstance( ObjectInstanceHandle theObject,
                                        ObjectClassHandle theObjectClass,
                                        String objectName )
            throws FederateInternalError
    {
        log( "Discoverd Object: handle=" + theObject + ", classHandle=" +
                theObjectClass + ", name=" + objectName );
    }

    @Override
    public void reflectAttributeValues( ObjectInstanceHandle theObject,
                                        AttributeHandleValueMap theAttributes,
                                        byte[] tag,
                                        OrderType sentOrder,
                                        TransportationTypeHandle transport,
                                        FederateAmbassador.SupplementalReflectInfo reflectInfo )
            throws FederateInternalError
    {
        // just pass it on to the other method for printing purposes
        // passing null as the time will let the other method know it
        // it from us, not from the RTI
        reflectAttributeValues( theObject,
                theAttributes,
                tag,
                sentOrder,
                transport,
                null,
                sentOrder,
                reflectInfo );
    }

    @Override
    public void reflectAttributeValues( ObjectInstanceHandle theObject,
                                        AttributeHandleValueMap theAttributes,
                                        byte[] tag,
                                        OrderType sentOrdering,
                                        TransportationTypeHandle theTransport,
                                        LogicalTime time,
                                        OrderType receivedOrdering,
                                        FederateAmbassador.SupplementalReflectInfo reflectInfo )
            throws FederateInternalError
    {
        StringBuilder builder = new StringBuilder( "Reflection for object:" );

        // print the handle
        builder.append( " handle=" + theObject );
        // print the tag
        builder.append( ", tag=" + new String(tag) );
        // print the time (if we have it) we'll get null if we are just receiving
        // a forwarded call from the other reflect callback above


        // print the attribute information
        builder.append( ", attributeCount=" + theAttributes.size() );
        builder.append( "\n" );
        for( AttributeHandle attributeHandle : theAttributes.keySet() )
        {
            // print the attibute handle
            builder.append( "\tattributeHandle=" );

            // Handle specific atttribute vaule
            if ( attributeHandle.equals(federate.statisticsAvgHandle) ) {
                builder.append( attributeHandle );
                builder.append( " (Average)    " );
                builder.append( ", attributeValue=" );
                HLAinteger32BE average = new HLA1516eInteger32BE();
                try {
                    average.decode(theAttributes.get(attributeHandle));
                } catch (DecoderException e) {
                    e.printStackTrace();
                }
                builder.append( average.getValue() );
                federate.avg = average.getValue();
            }
            else if( attributeHandle.equals(federate.statisticsDevHandle) ) {
                builder.append( attributeHandle );
                builder.append( " (Deviation)    " );
                builder.append( ", attributeValue=" );
                HLAinteger32BE dev = new HLA1516eInteger32BE();
                try {
                    dev.decode(theAttributes.get(attributeHandle));
                } catch (DecoderException e) {
                    e.printStackTrace();
                }
                builder.append( dev.getValue() );
                federate.deviation = dev.getValue();
            }
            else
            {
                builder.append( attributeHandle );
                builder.append( " (Unknown)   " );
            }

            builder.append( "\n" );
        }

        log( builder.toString() );
    }

    @Override
    public void receiveInteraction( InteractionClassHandle interactionClass,
                                    ParameterHandleValueMap theParameters,
                                    byte[] tag,
                                    OrderType sentOrdering,
                                    TransportationTypeHandle theTransport,
                                    FederateAmbassador.SupplementalReceiveInfo receiveInfo )
            throws FederateInternalError
    {
        // just pass it on to the other method for printing purposes
        // passing null as the time will let the other method know it
        // it from us, not from the RTI
        this.receiveInteraction( interactionClass,
                theParameters,
                tag,
                sentOrdering,
                theTransport,
                null,
                sentOrdering,
                receiveInfo );
    }

    @Override
    public void receiveInteraction( InteractionClassHandle interactionClass,
                                    ParameterHandleValueMap theParameters,
                                    byte[] tag,
                                    OrderType sentOrdering,
                                    TransportationTypeHandle theTransport,
                                    LogicalTime time,
                                    OrderType receivedOrdering,
                                    FederateAmbassador.SupplementalReceiveInfo receiveInfo )
            throws FederateInternalError
    {
        StringBuilder builder = new StringBuilder( "Interaction Received:" );

        // print the handle
        builder.append( " handle=" + interactionClass );
        if( interactionClass.equals(federate.deviceFailedHandle) )
        {
            builder.append( " (Device filed)" );
            for( ParameterHandle parameter : theParameters.keySet() )
            {
                builder.append( "\tDviceId Param!" );
                byte[] bytes = theParameters.get(federate.deviceIdHandle);
                HLAinteger32BE id = new HLA1516eInteger32BE();
                try {
                    id.decode(bytes);
                } catch (DecoderException e) {
                    e.printStackTrace();
                }
                int idDevice = id.getValue();
                builder.append( "\tcount Value=" + idDevice );
                federate.deviceList.get(idDevice).setOperational(false);
                federate.clientsList.get(idDevice).setServiceCalled(true);
            }
        }
        if( interactionClass.equals(federate.deviceRepairHandle) ) {
            int deviceId = 0;
            builder.append(" (Repair) ");
            for (ParameterHandle parameter : theParameters.keySet()) {
                if (parameter.equals(federate.deviceIdHandle)) {
                    builder.append("\tDevice Id Param!");
                    byte[] bytes = theParameters.get(federate.deviceIdHandle);
                    HLAinteger32BE dist = new HLA1516eInteger32BE();
                    try {
                        dist.decode(bytes);
                    } catch (DecoderException e) {
                        e.printStackTrace();
                    }
                    deviceId = dist.getValue();
                    for (ServiceMan srvm: this.federate.serviceManList) {
                        if (!srvm.isAvailable() && srvm.getClientId() == deviceId) {
                            srvm.setClientId(-1);
                            srvm.setAvailable(true);
                        }
                    }
                    builder.append("\tcount Value=" + deviceId);
                }
            }
            this.federate.clientsList.get(deviceId).setDeviceOperational(true);
            this.federate.clientsList.get(deviceId).setServiceCalled(false);
            this.federate.deviceList.get(deviceId).setOperational(true);
        }
        if( interactionClass.equals(federate.callServiceManHandle) ) {
            builder.append(" (CallServiceMan)");
            int distanceToClient = 0;
            int clientId = 0;
            for (ParameterHandle parameter : theParameters.keySet()) {
                if (parameter.equals(federate.distanceHandle)) {
                    builder.append("\tDistance Param!");
                    byte[] bytes = theParameters.get(federate.distanceHandle);
                    HLAinteger32BE dist = new HLA1516eInteger32BE();
                    try {
                        dist.decode(bytes);
                    } catch (DecoderException e) {
                        e.printStackTrace();
                    }
                    distanceToClient = dist.getValue();
                    builder.append("\tcount Value=" + distanceToClient);
                } else if (parameter.equals(federate.clientIdHandle)) {
                    builder.append("\tClientId Param!");
                    byte[] bytes = theParameters.get(federate.clientIdHandle);
                    HLAinteger32BE clntid = new HLA1516eInteger32BE();
                    try {
                        clntid.decode(bytes);
                    } catch (DecoderException e) {
                        e.printStackTrace();
                    }
                    clientId = clntid.getValue();
                    for (ServiceMan srvm: this.federate.serviceManList) {
                        if (srvm.isAvailable()) {
                            try {
                                srvm.setClientId(clientId);
                                srvm.setAvailable(false);
                                break;
                            } catch (Exception e) {
                              //  System.out.println(e.getMessage());
                            }
                        }
                    }
                    builder.append("\tcount Value=" + clientId);
                }
            }
        }
        // print the tag
        builder.append( ", tag=" + new String(tag) );
        // print the time (if we have it) we'll get null if we are just receiving
        // a forwarded call from the other reflect callback above
        if( time != null ) {
            builder.append( ", time=" + ((HLAfloat64Time)time).getValue() );
        }

        // print the parameer information
        builder.append( ", parameterCount=" + theParameters.size() );
        builder.append( "\n" );
        log( builder.toString() );
    }

    @Override
    public void removeObjectInstance( ObjectInstanceHandle theObject,
                                      byte[] tag,
                                      OrderType sentOrdering,
                                      FederateAmbassador.SupplementalRemoveInfo removeInfo )
            throws FederateInternalError
    {
        log( "Object Removed: handle=" + theObject );
    }

    //----------------------------------------------------------
    //                     STATIC METHODS
    //----------------------------------------------------------
}
