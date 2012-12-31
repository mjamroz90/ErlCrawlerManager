/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 31.12.12
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */

$(function(){

    var chunks = location.href.split('/');
    var id = chunks[chunks.length-2];
    $.getJSON("/ecm/stats/"+id.toString()+"?fetch",function(data){
        var jsonObject = data;
        var node2JsonStats = jsonObject[1]['statisticsList'];
        var result = processNodeJson(node2JsonStats);
        var options = {
            xaxis : {
                mode : "time",
                timeformat : "%Y-%m-%d-%H:%M"
            }
        };
        $.plot($('#totalProcessedSitesNum'),[result['totalProcessedSitesNum']],options);
        $.plot($('#meanSiteProcessingNum'),[result['meanSiteProcessingNum']],options);
        $.plot($('#totalAddressesFetchedNum'),[result['totalAddressesFetchedNum']],options);
        $.plot($('#partAddressesNumPerSite'),[result['partAddressesNumPerSite']],options);
        $.plot($('#meanAddressesNumPerSite'),[result['meanAddressesNumPerSite']],options);
        $.plot($('#memoryUsage'),[result['memoryUsage']],options);
        $.plot($('#meanProcessorUsage'),[result['meanProcessorUsage']],options);
    });

});

//nodeJsonStats = [{id,meanAddressesNumPerSite,meanProcessorUsage,meanSiteProcessingNum,memoryUsage,partAddressesNumPerSite,
// reportedAsString,totalAddressesFetchedNum,totalProcessedSitesNum},{..},..]
function processNodeJson(nodeJsonStats){
    var result = {};
    result['meanAddressesNumPerSite'] = [];
    result['meanProcessorUsage'] = [];
    result['meanSiteProcessingNum'] = [];
    result['memoryUsage'] = [];
    result['partAddressesNumPerSite'] = [];
    result['totalAddressesFetchedNum'] = [];
    result['totalProcessedSitesNum'] = [];

    for (var i=0; !(i >= nodeJsonStats.length); i++){
        var tuple = nodeJsonStats[i];
        var reported = tuple['reported'];
        result['meanAddressesNumPerSite'].push([reported,tuple['meanAddressesNumPerSite']]);
        result['meanProcessorUsage'].push([reported,tuple['meanProcessorUsage']]);
        result['meanSiteProcessingNum'].push([reported,tuple['meanSiteProcessingNum']]);
        result['memoryUsage'].push([reported,tuple['memoryUsage']]);
        result['partAddressesNumPerSite'].push([reported,tuple['partAddressesNumPerSite']]);
        result['totalAddressesFetchedNum'].push([reported,tuple['totalAddressesFetchedNum']]);
        result['totalProcessedSitesNum'].push([reported,tuple['totalProcessedSitesNum']]);
    }

    return result;
};
